package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class Powerup extends GameObject {
    private static Bitmap _sprite;
    private static final Vector2 size = new Vector2(0, 0);
    private final AnimatedSprite _animatedSprite;
    private float _lifeTime;

    public Powerup(int filepath, float xPosFlex, float yPosFlex, float lifeTime, boolean isStatic) {
        type = TYPE.POWER_UP;

        if (_sprite == null)
        {
            _sprite = FileSystem.LoadScaledSprite(filepath, 1.5f, 1.5f, true);
            size.x = (float) _sprite.getWidth() / 5;
            size.y = (float) _sprite.getHeight();
        }
        _animatedSprite = new AnimatedSprite(_sprite, 1,  4, 24);

        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
        rigidbody._size = size;

        _lifeTime = lifeTime;
        if (!isStatic) rigidbody._InitDynamicBody(1);
    }
    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        if (_isActive)
        {
            _animatedSprite.update(dt);

            if (_lifeTime > 0f) _lifeTime -= dt;
            else onDisable();
        }
    }

    public void onEnable(float xPos, float yPos, float lifeTime) {
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPos;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPos;
        _lifeTime = lifeTime;
        _isActive = true;
    }
    @Override
    public void onDisable() {
        _isActive = false;
        _lifeTime = 10;
        rigidbody._position.x = -10000.0f;
        rigidbody._position.y = -10000.0f;
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
