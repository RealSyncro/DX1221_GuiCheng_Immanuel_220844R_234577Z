package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameObject;

public class FpsText extends GameObject {
    private final int _screenWidth;
    private final int _screenHeight;
    private final Paint _paint;
    private int _frameCount;
    private float _fps;
    private long _lastTime;

    public FpsText(int color) {
        _paint = new Paint();
        _paint.setColor(color);
        _paint.setTextSize(150);
        _paint.setTextAlign(Paint.Align.RIGHT);
        _screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        _screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        _frameCount++;
        long currentTime = System.currentTimeMillis();
        if(currentTime - _lastTime > 1000) {
            _fps = (_frameCount * 1000.f) / (currentTime - _lastTime);
            _lastTime = currentTime;
            _frameCount = 0;
        }
    }

    @Override
    public void onUpdate() {}

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText("FPS: " + (int)_fps, _screenWidth - 100, _screenHeight - 100, _paint);
    }
}
