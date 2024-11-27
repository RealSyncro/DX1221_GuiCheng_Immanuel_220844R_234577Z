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
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.CollisionManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.Physics2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.BackgroundObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.FPSText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.ScoreText;
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

    private  boolean PowerupActive = false;
    private float PowerUpDuration = 0f;

    private Vector2 snap = Vector2.zero();
    private PlayerObject player = null;
    private ScoreText scoreText = null;
    private Physics2D PhysicsWorld = null;
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

        AudioManager.Get().EnableVibration(vibrator, vibratorManager);
    }

    public void onEnter() {
        super.onEnter();
        AudioManager.Get().TerminateSFXPlayer();

        // Initialise GameObject & UIObject List
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
        _gameEntities.add(new BoundingBox(0, 50, R.drawable.collider, true));
        _gameEntities.add(new BoundingBox(110, 50, R.drawable.collider, true));
        _gameEntities.add(new BoundingBox(50, 0, R.drawable.collider, false));
        _gameEntities.add(new BoundingBox(50, 100, R.drawable.collider, false));

        _platformEntities.add(new PlatformObject(R.drawable.platform_3x1, 50, 75, 8f, true));
        totalPlatforms += 1;

        // Create new Physics Simulation
        PhysicsWorld = new Physics2D(50000f);
        AudioManager.Get().PlayBGM(GameActivity.instance, R.raw.game_bg);
    }

    @Override
    public void onUpdate(float dt) {

        // Platform + Coin Spawner Algorithm Timer
        spawnTimer = spawnTimer > 0f ? spawnTimer - dt : SpawnPlatform();
        coinSpawnTimer = coinSpawnTimer > 0f ? spawnTimer -= dt : SpawnCoin();
        Random rand = new Random();
        int randSpawn = 1 + rand.nextInt(2);
        if(randSpawn == 1)
            TrickOrTrickTimer = TrickOrTrickTimer > 0f? spawnTimer -= dt:SpawnFireBall();
        else {
            TrickOrTrickTimer = TrickOrTrickTimer > 0f? spawnTimer -= dt:SpawnSpikeBall();
        }

        TrickOrTrickTimer = TrickOrTrickTimer > 0f? spawnTimer -= 5 - dt:SpawnPowerup();

        if(dt == PowerUpDuration)
            PowerupActive = false;
        // Update all User Interface Elements.
        for (UIObject ui : _uiEntities) {ui.onUpdate(dt);}

        for (GameObject entity : _gameEntities) {
            entity.onUpdate(dt);

            // Compares player with other game objects for resolution
            if (entity instanceof PlayerObject) {
                for (GameObject other : _gameEntities) {

                    // Coin Collided.
                    if (other instanceof CoinObject && CollisionManager.isColliding(entity, other)) {
                        other.destroy();
                        scoreText.IncrementScore(1);
                        AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.collect_coin);
                        AudioManager.Get().PlayVibration(100, 10);
                    }
                    if(other instanceof  FireBall && CollisionManager.isColliding(entity, other)){
                        if(PowerupActive)
                            return;
                        else{
                            AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.firesound);
                            AudioManager.Get().PlayVibration(100, 10);
                            GameActivity.instance.Gameover(scoreText.GetScore());
                            return;
                        }
                    }
                    if(other instanceof  SpikeBall && CollisionManager.isColliding(entity, other)){
                        if(PowerupActive)
                            return;
                        else{
                            AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.spikesound);
                            AudioManager.Get().PlayVibration(100, 10);
                            GameActivity.instance.Gameover(scoreText.GetScore());
                            return;
                        }
                    }
                    if(other instanceof  Powerup && CollisionManager.isColliding(entity, other)){
                        PowerupActive = true;
                        AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.powerupsound);
                        AudioManager.Get().PlayVibration(100, 10);
                        PowerUpDuration = dt + 10f;
                    }

                    // Screen Border collided.
                    if (other instanceof BoundingBox && CollisionManager.isColliding(entity, other)) {
                        Vector2 snapAdd;

                        if (((BoundingBox) other)._isHorizontal)
                            snapAdd = CollisionManager.ResolutionXCalc(player, other);
                        else {
                            GameActivity.instance.Gameover(scoreText.GetScore());
                            return;
                        }
                        snap.x += snapAdd.x;
                        snap.y += snapAdd.y;
                    }
                }
            }
        }

        for (GameObject platform : _platformEntities) {
            platform.onUpdate(dt);

            // Checks whether player is on a platform. Increments 1 if not on a platform.
            if (CollisionManager.isColliding(player, platform) && player.jumpTimer <= 0f) {
                Vector2 snapAdd = CollisionManager.ResolutionYCalc(player, platform);
                snap.x += snapAdd.x;
                snap.y += snapAdd.y;

                if (snap.y >= 0f)
                    checked -= 1;
            }
            else {
                checked += 1;
            }
        }

        // Resolves player collision
        if (player != null) {
            player.rigidbody._position.x += snap.x;
            player.rigidbody._position.y += snap.y;

            // Sets Grounded status based on the combined platform checks.
            if (checked < totalPlatforms)
                player.rigidbody._isGrounded = true;
            else if (checked == totalPlatforms)
                player.rigidbody._isGrounded = false;

            // Resets used variables
            snap = Vector2.zero();
            checked = 0;
        }

        // Updates physics of all affected rigidbody.
        PhysicsWorld.onUpdate(dt, _gameEntities, _platformEntities);

        // Clean up "destroyed" entities by removing them
        DestroyObjects();
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
        AudioManager.Get().TerminateSFXPlayer();
        FreeMemory();
    }

    // GameObject Randomised Spawner Algorithm
    private float SpawnPlatform() {
        float spawnDuration = 3f;
        Random rand = new Random();
        int randX = rand.nextInt((80 - 40 + 1) + 40);
        _platformEntities.add(new PlatformObject(R.drawable.platform_3x1, randX, 0, 10f, false));
        totalPlatforms += 1;
        return spawnDuration;
    }

    private float SpawnCoin() {
        float coinDuration = 5f;
        Random rand = new Random();
        int randX = rand.nextInt((80 - 40 + 1) + 40);
        _gameEntities.add(new CoinObject(R.drawable.flystar, randX, 0, 10f, false));
        return coinDuration;
    }
    private float SpawnPowerup() {
        float Powerduration = 5f;
        Random rand = new Random();
        int randX = rand.nextInt((80 - 40 + 1) + 40);
        _gameEntities.add(new Powerup(R.drawable.powerup, randX, 0, 10f, false));
        return Powerduration;
    }
    private float SpawnFireBall() {
        float FireDuration = 5f;
        Random rand = new Random();
        int randX = rand.nextInt((80 - 40 + 1) + 40);
        _gameEntities.add(new FireBall(R.drawable.fire, randX, 0, 10f, false));
        return FireDuration;
    }
    private float SpawnSpikeBall() {
        float SpikeDuration = 5f;
        Random rand = new Random();
        int randX = rand.nextInt((80 - 40 + 1) + 40);
        _gameEntities.add(new SpikeBall(R.drawable.suriken, randX, 0, 10f, false));
        return SpikeDuration;
    }

    // Removed Destroyed Objects during runtime.
    private void DestroyObjects() {

        for (int i = _gameEntities.size() - 1; i >= 0; i--) {
            if (_gameEntities.get(i).canDestroy()) {
                _gameEntities.remove(i);
            }
        }

        for (int i = _platformEntities.size() - 1; i >= 0; i--) {
            if (_platformEntities.get(i).canDestroy()) {
                _platformEntities.remove(i);
                totalPlatforms -= 1;
            }
        }
    }
    private void FreeMemory() {
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
        snap = new Vector2(0f, 0f);
        player = null;
        scoreText = null;
        PhysicsWorld = null;
        spawnTimer = 0f;
        coinSpawnTimer = 0f;
    }
}


