package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Canvas;
import android.graphics.Color;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.CollisionManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.Physics2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.FpsText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {
    private GameObject player = null;
    private final Physics2D PhysicsWorld = new Physics2D(60f);
    private final AudioManager AudioController = new AudioManager();
    private final Vector<GameObject> _gameEntities = new Vector<>();
    private final Vector<GameObject> _uiEntities = new Vector<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onEnter() {
        super.onEnter();
        _uiEntities.add(new BackgroundObject());
        _uiEntities.add(new FpsText(Color.rgb(255, 255, 255)));

        player = new PlayerObject();
        _gameEntities.add(player);
        _gameEntities.add(new CoinObject());
        _gameEntities.add(new PlatformObject(R.drawable.platform_3x1));
        AudioController.PlayBGM(R.raw.lofi_background);
    }

    @Override
    public void onUpdate(float dt) {
        Vector2 snap = Vector2.zero();

        for (GameObject ui : _uiEntities) {
            ui.onUpdate(dt);
        }

        for (GameObject entity : _gameEntities) {
            entity.onUpdate(dt);

            // Compares player with other game objects for resolution
            if (entity instanceof PlayerObject) {
                for (GameObject other : _gameEntities)
                {
                    // Coin Collided
                    if (other instanceof CoinObject && CollisionManager.isColliding(entity, other)) {
                        System.out.println("Is Colliding!");
                        other.destroy();
                        AudioController.PlaySFX(R.raw.sonic_ring_sound);
                        AudioController.PlayVibration(100, 10);
                    }

                    // Player collided with wall or platform
                    if (CollisionManager.isColliding(entity, other))
                        snap.add(CollisionManager.ResolutionCalc(entity, other));
                }
            }
        }

        // Clean up "destroyed" entities by removing them
        for (int i = _gameEntities.size() - 1; i >= 0; i--) {
            if (_gameEntities.get(i).canDestroy())
                _gameEntities.remove(i);
        }

        // Resolves player collision
        if (player != null) player.rigidbody._position.add(snap);
        // Updates physics of all affected rigidbody.
        PhysicsWorld.onUpdate(dt, _gameEntities);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#b2d4ff"));

        for (GameObject ui : _uiEntities) {ui.onRender(canvas);}
        for (GameObject entity : _gameEntities) {entity.onRender(canvas);}

    }

    @Override
    public void onExit() {
        AudioController.StopAllSFXPlayer();
    }
}
