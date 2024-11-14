package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Rigidbody2D;

public class PauseButton extends GameObject {

    private final Bitmap _sprite;
    private final float dstLeft;
    private final float dstTop;

    public PauseButton() {
        rigidbody._position.x = (float) (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 16) * 1;
        rigidbody._position.y = (float) (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 16) * 3;
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        Bitmap sprite = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * 2f), (int) (bmp.getHeight() * 2f), true);

        _sprite = Bitmap.createScaledBitmap(sprite, 144, 144, true);
        dstLeft = rigidbody._position.x - (float) _sprite.getWidth() / 2;
        dstTop = rigidbody._position.y - (float) _sprite.getHeight();
    }

    @Override
    public void onUpdate(float dt) {
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();

        if (motionEvent == null) return;

        float tapPositionX = motionEvent.getX();
        float tapPositionY = motionEvent.getY();
        int buttonBMPX = _sprite.getWidth();
        int buttonBMPY = _sprite.getHeight();


        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN &&
                !BackDialog.isShowing() &&
                tapPositionX >= rigidbody._position.x - (float) (buttonBMPX / 2) + 10f &&
                tapPositionX <= rigidbody._position.x + (float) (buttonBMPX / 2) + 10f &&
                tapPositionY >= rigidbody._position.y - (float) (buttonBMPY / 2) + 10f &&
                tapPositionY <= rigidbody._position.y + (float) (buttonBMPY / 2) + 10f
        )
        {
            System.out.println("Position - X: " + rigidbody._position.x + " Y: " + rigidbody._position.y);
            System.out.println("Tap Position - X: " + tapPositionX + " Y: " + tapPositionY);

            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(), "Back dialog");
        }
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_sprite, dstLeft, dstTop, null);
    }
}
