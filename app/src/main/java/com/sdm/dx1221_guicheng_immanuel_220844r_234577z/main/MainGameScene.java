package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Canvas;
import android.graphics.Color;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.CollisionManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.Physics2D;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.FpsText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.PauseButton;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {
    private GameObject player = null;
    private final Physics2D PhysicsWorld = new Physics2D(60f);
    private final AudioManager AudioController = new AudioManager();
    private final Vector<GameObject> _gameEntities = new Vector<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onEnter() {
        super.onEnter();
        _gameEntities.add(new BackgroundObject());

        player = new PlayerObject();
        _gameEntities.add(player);

        _gameEntities.add(new CoinObject());
        _gameEntities.add(new PlatformObject(R.drawable.flystar));

        _gameEntities.add(new PauseButton());
        _gameEntities.add(new FpsText(0));

        AudioController.PlayBGM(R.raw.shinytech);
    }

    @Override
    public void onUpdate(float dt) {
        Vector2 snap = Vector2.zero();

        for (GameObject entity : _gameEntities) {
            entity.onUpdate(dt);

            if (entity instanceof PlayerObject) {
                for (GameObject other : _gameEntities)
                {
                    if (other instanceof CoinObject && CollisionManager.isColliding(entity, other)) {
                        System.out.println("Is Colliding!");
                        other.destroy();
                        AudioController.PlaySFX(R.raw.sonic_ring_sound);
                        AudioController.PlayVibration(100, 10);
                    }

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

        player.rigidbody._position.add(snap);
        PhysicsWorld.onUpdate(dt, _gameEntities);
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#b2d4ff"));
        for (GameObject entity : _gameEntities) {
            entity.onRender(canvas);
        }
    }

    @Override
    public void onExit() {
        AudioController.StopAllSFXPlayer();
    }
}
