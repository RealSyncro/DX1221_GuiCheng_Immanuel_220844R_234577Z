package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class PlatformObject extends GameObject {
    private static Bitmap _sprite = null;
    private static final Vector2 size = new Vector2(0, 0);
    private final AnimatedSprite _animatedSprite;
    private float _lifeTime;
    public PlatformObject(int resID, int xPosFlex, int yPosFlex, float lifeTime, boolean isStatic) {

        if (_sprite == null)
        {
            _sprite =  FileSystem.LoadScaledSprite(resID, 0.25f, 0.25f, true);
            size.x = (float) _sprite.getWidth();
            size.y = (float) _sprite.getHeight();
        }

        _animatedSprite = new AnimatedSprite(_sprite, 1, 1, 1);

        if (!isStatic) rigidbody._InitDynamicBody(1f);
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
        rigidbody._size = size;

        _lifeTime = lifeTime;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

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
