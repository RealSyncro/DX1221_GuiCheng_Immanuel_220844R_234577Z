package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.AnimatedSprite;

public class CoinObject extends GameObject {

    private final AnimatedSprite _animatedSprite;

    public CoinObject() {
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 4);
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 3);

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.flystar);
        Bitmap _sprite = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 1.5), (int) (bmp.getHeight() * 1.5), true);
        _animatedSprite = new AnimatedSprite(_sprite, 1,  5, 24);
        rigidbody._size = new Vector2((float) _sprite.getWidth() / 5, (float) _sprite.getHeight());
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        _animatedSprite.update(dt);
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        _animatedSprite.render(canvas, (int) rigidbody._position.x, (int) rigidbody._position.y, null);
    }
}
