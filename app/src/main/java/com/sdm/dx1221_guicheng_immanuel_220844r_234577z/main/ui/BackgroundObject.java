package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.UIObject;

public class BackgroundObject extends UIObject {
    private static Bitmap _backgroundBitmap0;
    private static Bitmap _backgroundBitmap1;
    private float _backgroundPosition;
    private final int screenWidth;
    private final int screenHeight;

    public BackgroundObject() {
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        if (_backgroundBitmap0 == null || _backgroundBitmap1 == null)
        {
            Bitmap bmp = FileSystem.LoadCustomSprite(R.drawable.bg_night, screenWidth, screenHeight, true);
            _backgroundBitmap0 = bmp;
            _backgroundBitmap1 = bmp;
        }
    }

    @Override
    public void onUpdate(float dt) {
        _backgroundPosition = (_backgroundPosition - dt * 500) % (float)screenHeight;
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_backgroundBitmap0, 0, _backgroundPosition, null);
        canvas.drawBitmap(_backgroundBitmap1, 0, _backgroundPosition + screenHeight, null);
    }
}
