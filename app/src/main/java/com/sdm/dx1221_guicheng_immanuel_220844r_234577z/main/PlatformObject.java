package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

import java.io.File;

public class PlatformObject extends GameObject {
    private final AnimatedSprite _animatedSprite;
    private final Bitmap _collider = null;

    public PlatformObject(int resID) {
        Bitmap _sprite =  FileSystem.LoadScaledSprite(resID, 0.5f, 0.5f, true);
        _animatedSprite = new AnimatedSprite(_sprite, 1, 1, 1);

        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 16) * 6f;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 16) * 12f;
        rigidbody._size = new Vector2((float) _sprite.getWidth(), (float) _sprite.getHeight());

//        _collider = FileSystem.LoadCustomSprite(R.drawable.collider,
//                _sprite.getWidth(), _sprite.getHeight(), true);
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.onRender(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
