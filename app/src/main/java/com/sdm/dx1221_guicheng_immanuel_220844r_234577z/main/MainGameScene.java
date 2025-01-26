package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibratorManager;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.BoundingBox;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.CoinObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.FireBall;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.PlatformObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.Powerup;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects.SpikeBall;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.CollisionManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.Physics2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui.BackgroundObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui.FPSText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui.ScoreText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.UIObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Random;
import java.util.Vector;

public class MainGameScene extends GameScene {
    private int totalPlatforms = 0, checked = 0;
    private float spawnTimer = 5f;
    private float coinSpawnTimer = 6f;
    private float TrickOrTrickTimer = 12f;
    private float PowerUpDuration = 0f;
    private boolean PowerUpActive = false;
    private final Random RNG_GEN = new Random();

    private final Vector2 snap = new Vector2(0, 0);
    private PlayerObject player = null;
    private ScoreText scoreText = null;
    private Physics2D PhysicsWorld = null;
    private Vector<GameObject> _goPool;
    private Vector<GameObject> _gameEntities;
    private Vector<GameObject> _platformEntities;
    private Vector<UIObject> _uiEntities;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onCreate() {
        super.onCreate();

        Vibrator vibrator = null;
        VibratorManager vibratorManager = null;

        // Device Compatibility Check for Different Vibration Classes
        if (Build.VERSION.SDK_INT >= 31) {
            vibratorManager = (VibratorManager) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        }
        else if (Build.VERSION.SDK_INT >= 26) {
            vibrator = (Vibrator) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        }

        AudioController.Get().EnableVibration(vibrator, vibratorManager);
    }

    public void onEnter() {
        super.onEnter();
        AudioController.Get().StopAllSFXPlayer();

        _goPool = new Vector<>();
        _gameEntities = new Vector<>();
        _platformEntities = new Vector<>();
        _uiEntities = new Vector<>();

        // Add UI Elements
        scoreText = new ScoreText(Color.rgb(0, 255, 0), 100, Paint.Align.RIGHT, 95, 10);
        _uiEntities.add(new BackgroundObject());
        _uiEntities.add(new FPSText(Color.rgb(255, 255, 255), 100, Paint.Align.RIGHT, 95, 5));
        _uiEntities.add(scoreText);

        // Add Actor Elements
        player = new PlayerObject();
        _gameEntities.add(player);
        _gameEntities.add(new BoundingBox(0, 50, R.drawable.collider, true, true));
        _gameEntities.add(new BoundingBox(110, 50, R.drawable.collider, true, true));
        _gameEntities.add(new BoundingBox(50, 0, R.drawable.collider, false, true));
        _gameEntities.add(new BoundingBox(50, 100, R.drawable.collider, false, false));

        PreInit_GO_POOL();

        PlatformObject PO = new PlatformObject(R.drawable.platform_3x1, 50, 75, 5f, true);
        PO._isActive = true;

        _platformEntities.add(PO);
        totalPlatforms += 1;

        // Create new Physics Simulation
        PhysicsWorld = new Physics2D(30000f);
        AudioController.Get().PlayBGM(GameActivity.instance, R.raw.game_bg);
    }

    @Override
    public void onUpdate(float dt) {
        // Platform + Coin Spawner Algorithm Timer
        spawnTimer = spawnTimer > 0f ? spawnTimer - dt : SpawnPlatform();
        coinSpawnTimer = coinSpawnTimer > 0f ? spawnTimer -= dt : SpawnCoin();

        if (TrickOrTrickTimer > 0f) TrickOrTrickTimer -= dt;
        else {
            int randSpawn = RNG_GEN.nextInt(2);
            switch (randSpawn)
            {
                case 0:
                    TrickOrTrickTimer = SpawnFireBall();
                    break;
                case 1:
                    TrickOrTrickTimer = SpawnSpikeBall();
                    break;
                default:
                    TrickOrTrickTimer = SpawnPowerUp();
                    break;
            }
        }

        if(PowerUpDuration <= 0f) PowerUpActive = false;
        else PowerUpDuration = PowerUpDuration > 0f ? PowerUpDuration - dt : 0f;


        // Update all User Interface Elements.
        for (UIObject ui : _uiEntities) {ui.onUpdate(dt);}

        for (GameObject entity : _gameEntities) {
            entity.onUpdate(dt);

            // Compares player with other game objects for resolution
            if (entity instanceof PlayerObject) {
                for (GameObject other : _gameEntities) {

                    // Coin Collided.
                    if (other instanceof CoinObject && CollisionManager.isColliding(entity, other)) {
                        other.onDisable();
                        scoreText.IncrementScore(1);
                        AudioController.Get().PlaySFX(R.raw.collect_coin);
                        AudioController.Get().PlayVibration(100, 10);
                        continue;
                    }
                    // Fire-ball collided
                    if(other instanceof FireBall && CollisionManager.isColliding(entity, other)){
                        if (PowerUpActive) return;

                        AudioController.Get().PlaySFX(R.raw.firesound);
                        AudioController.Get().PlayVibration(100, 10);
                        GameActivity.instance.GameOver(scoreText.GetScore());
                        return;
                    }
                    // Spike-ball collided
                    if(other instanceof SpikeBall && CollisionManager.isColliding(entity, other)){
                        if(PowerUpActive) return;

                        AudioController.Get().PlaySFX(R.raw.spikesound);
                        AudioController.Get().PlayVibration(100, 10);
                        GameActivity.instance.GameOver(scoreText.GetScore());
                        return;
                    }
                    // Power-Up Collided
                    if(other instanceof Powerup && CollisionManager.isColliding(entity, other)){
                        PowerUpActive = true;
                        AudioController.Get().PlaySFX(R.raw.powerupsound);
                        AudioController.Get().PlayVibration(100, 10);
                        PowerUpDuration = 10f;
                        continue;
                    }
                    // Screen Border collided.
                    if (other instanceof BoundingBox && CollisionManager.isColliding(entity, other)) {
                        if (((BoundingBox) other)._isHorizontal) {
                            Vector2 snapAdd;
                            snapAdd = CollisionManager.ResolutionXCalc(player, other);
                            snap.add(snapAdd);
                        }
                        else {
                            GameActivity.instance.GameOver(scoreText.GetScore());
                            return;
                        }
                    }
                }
            }

            if (entity instanceof BoundingBox)
            {
                BoundingBox BB = (BoundingBox) entity;
                if (!BB._ignoreSpawn)
                {
                    for (int i = 0; i < _gameEntities.size(); i++) {
                        GameObject go = _gameEntities.get(i);

                        if (go._isActive)
                        {
                            if (    go.type == GameObject.TYPE.COIN ||
                                    go.type == GameObject.TYPE.FIRE_BALL ||
                                    go.type == GameObject.TYPE.SPIKE_BALL ||
                                    go.type == GameObject.TYPE.POWER_UP ||
                                    go.type == GameObject.TYPE.PLATFORM)
                            {
                                if (CollisionManager.isColliding(entity, go))
                                    go.onDisable();
                            }
                        }
                    }
                }
            }
        }

        for (GameObject platform : _platformEntities) {
            platform.onUpdate(dt);

            // Checks whether player is on a platform. Increments 1 if not on a platform.
            if (platform._isActive)
            {
                if (CollisionManager.isColliding(player, platform) && player.jumpTimer <= 0f) {
                    Vector2 snapAdd = CollisionManager.ResolutionYCalc(player, platform);
                    snap.add(snapAdd);
                    if (snap.y >= 0f) checked -= 1;
                }
                else {
                    checked += 1;
                }
            }
        }

        // Resolves player collision
        if (player != null) {
            player.rigidbody._position.add(snap);

            // Sets Grounded status based on the combined platform checks.
            if (checked < totalPlatforms)
                player.rigidbody._isGrounded = true;
            else if (checked == totalPlatforms)
                player.rigidbody._isGrounded = false;

            // Resets used variables
            snap.SetZero();
            checked = 0;
        }

        // Updates physics of all affected rigidbody.
        PhysicsWorld.onUpdate(dt, _gameEntities, _platformEntities);

        // Clean up "in-active" entities by removing them
        DeactivateObjects();
    }

    @Override
    public void onRender(Canvas canvas) {
        if (_uiEntities != null) for (UIObject ui : _uiEntities) {ui.onRender(canvas);}
        if (_gameEntities != null) for (GameObject entity : _gameEntities) {entity.onRender(canvas);}
        if (_platformEntities != null) for (GameObject platform : _platformEntities) {platform.onRender(canvas);}
    }

    @Override
    public void onExit() {
        // Release memory allocation in MediaPlayer and GameObject/UIObject List.
        AudioController.Get().StopAllSFXPlayer();
        FreeMemory();
    }


    // Object Pooling
    private void PreInit_GO_POOL()
    {
        for (int i = 0; i < 8; i++)
            _goPool.add(new Powerup(R.drawable.powerup, 0, 0, 10, false));

        for (int i = 0; i < 8; i++)
            _goPool.add(new CoinObject(R.drawable.flystar, 0, 0, 10, false));

        for (int i = 0; i < 8; i++)
            _goPool.add(new PlatformObject(R.drawable.platform_3x1, 0, 0, 10, false));

        for (int i = 0; i < 8; i++)
            _goPool.add(new FireBall(R.drawable.fire, 0, 0, 10, false));

        for (int i = 0; i < 8; i++)
            _goPool.add(new SpikeBall(R.drawable.suriken, 0, 0, 10, false));
    }
    private GameObject FetchGO(GameObject.TYPE goType)
    {
        // Check if there is an inactive instance available.
        for (GameObject go : _goPool) {
            if (go.type == goType && !go._isActive) return go;
        }

        // Instantiate a small pool of requested game object type.
       if (goType == GameObject.TYPE.POWER_UP)
       {
           for (int i = 0; i < 4; i++)
               _goPool.add(new Powerup(R.drawable.powerup, 0, 0, 10, false));
       }
       else if (goType == GameObject.TYPE.COIN)
       {
           for (int i = 0; i < 4; i++)
               _goPool.add(new CoinObject(R.drawable.flystar, 0, 0, 10, false));
       }
       else if (goType == GameObject.TYPE.PLATFORM)
       {
           for (int i = 0; i < 4; i++)
               _goPool.add(new PlatformObject(R.drawable.platform_3x1, 0, 0, 10, false));
       }
       else if (goType == GameObject.TYPE.FIRE_BALL)
       {
           for (int i = 0; i < 4; i++)
               _goPool.add(new FireBall(R.drawable.fire, 0, 0, 10, false));
       }
       else if (goType == GameObject.TYPE.SPIKE_BALL)
       {
           for (int i = 0; i < 4; i++)
               _goPool.add(new SpikeBall(R.drawable.suriken, 0, 0, 10, false));
       }
       return FetchGO(goType);
    }

    // Remove Inactive objects from collision array during runtime.
    private void DeactivateObjects() {
        for (int i = _gameEntities.size() - 1; i >= 0; i--) {
            if (!_gameEntities.get(i)._isActive) {
                _gameEntities.remove(i);
            }
        }

        for (int i = _platformEntities.size() - 1; i >= 0; i--) {
            if (!_platformEntities.get(i)._isActive) {
                _platformEntities.remove(i);
                totalPlatforms -= 1;
            }
        }
    }
    private void FreeMemory() {
        if (!_goPool.isEmpty()) {
            _goPool.subList(0, _goPool.size()).clear();
        }

        if (!_gameEntities.isEmpty()) {
            _gameEntities.subList(0, _gameEntities.size()).clear();
        }

        if (!_uiEntities.isEmpty()) {
            _uiEntities.subList(0, _uiEntities.size()).clear();
        }

        if (!_platformEntities.isEmpty()) {
            _platformEntities.subList(0, _platformEntities.size()).clear();
        }

        totalPlatforms = 0;
        checked = 0;
        snap.SetZero();
        player = null;
        scoreText = null;
        PhysicsWorld = null;
        spawnTimer = 0f;
        coinSpawnTimer = 0f;
    }




    // GameObject Randomised Spawner Algorithm
    private float SpawnPlatform() {
        int randX = RNG_GEN.nextInt((80 - 40 + 1) + 40);

        PlatformObject go = (PlatformObject) FetchGO(GameObject.TYPE.PLATFORM);
        go.onEnable(randX, 0, 10f);
        _platformEntities.add(go);
        totalPlatforms += 1;

        return 3.0f;
    }

    private float SpawnCoin() {
        int randX = RNG_GEN.nextInt((80 - 40 + 1) + 40);

        CoinObject go = (CoinObject) FetchGO(GameObject.TYPE.COIN);
        go.onEnable(randX, 0, 10f);
        _gameEntities.add(go);

        return 5.0f;
    }
    private float SpawnPowerUp() {
        int randX = RNG_GEN.nextInt((80 - 40 + 1) + 40);

        Powerup go = (Powerup) FetchGO(GameObject.TYPE.POWER_UP);
        go.onEnable(randX, 0, 10f);
        _gameEntities.add(go);

        return 5.0f;
    }
    private float SpawnFireBall() {
        int randX = RNG_GEN.nextInt((80 - 40 + 1) + 40);

        FireBall go = (FireBall) FetchGO(GameObject.TYPE.FIRE_BALL);
        go.onEnable(randX, 0, 10f);
        _gameEntities.add(go);

        return 5.0f;
    }
    private float SpawnSpikeBall() {
        int randX = RNG_GEN.nextInt((80 - 40 + 1) + 40);

        SpikeBall go = (SpikeBall) FetchGO(GameObject.TYPE.SPIKE_BALL);
        go.onEnable(randX, 0, 10f);
        _gameEntities.add(go);

        return 5.0f;
    }
}


