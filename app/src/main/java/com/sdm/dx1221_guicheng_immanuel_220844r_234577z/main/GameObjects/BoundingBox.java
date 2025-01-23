package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class BoundingBox extends GameObject {
    private final AnimatedSprite _animatedSprite;
    public boolean _isHorizontal;

    public BoundingBox(int xPosFlex, int yPosFlex, int filepath, boolean isHorizontal) {
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
        Bitmap _sprite;

        if (isHorizontal)
            _sprite = FileSystem.LoadScaledSprite(filepath, 0.1f, 10f, true);
        else
            _sprite = FileSystem.LoadScaledSprite(filepath, 10f, 0.05f, true);

        _animatedSprite = new AnimatedSprite(_sprite, 1,  5, 24);
        rigidbody._size = new Vector2((float) _sprite.getWidth(), (float) _sprite.getHeight());
        _isHorizontal = isHorizontal;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
//        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
