package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class PlatformObject extends GameObject {
    private final Bitmap sprite;

    public PlatformObject(int resID) {
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 4);
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 3);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), resID);

        sprite = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 1.5), (int) (bmp.getHeight() * 1.5), true);
        rigidbody._size = new Vector2((float) sprite.getWidth(), (float) sprite.getHeight());
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
