package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class CoinObject extends GameObject {
    private static Bitmap _sprite;
    private final AnimatedSprite _animatedSprite;
    private static final Vector2 size = new Vector2(0, 0);
    private float _lifeTime;

    public CoinObject(int filepath, float xPosFlex, float yPosFlex, float lifeTime, boolean isStatic) {
        if (_sprite == null)
        {
            _sprite = FileSystem.LoadScaledSprite(filepath, 1.5f, 1.5f, true);
            size.x = (float) _sprite.getWidth() / 5;
            size.y = (float) _sprite.getHeight();
        }

        _animatedSprite = new AnimatedSprite(_sprite, 1,  5, 24);
        _animatedSprite.AddAnimation("idle", 0, 4);
        _animatedSprite.PlayAnimation("idle");

        if (!isStatic) rigidbody._InitDynamicBody(1);
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
        rigidbody._size = size;

        _lifeTime = lifeTime;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        _animatedSprite.update(dt);

        if (_lifeTime > 0f) _lifeTime -= dt;
        else destroy();
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
