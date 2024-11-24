package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.TextUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.UIObject;

public class FPSText extends UIObject {
    private final TextUI text;
    private int _frameCount;
    private float _fps;
    private long _lastTime;

    public FPSText(int color, int size, Paint.Align align, int xPosFlex, int yPosFlex) {
        text = new TextUI(color, size, align, xPosFlex, yPosFlex);
    }

    @Override
    public void onUpdate(float dt) {
        _frameCount++;
        long currentTime = System.currentTimeMillis();
        if(currentTime - _lastTime > 1000) {
            _fps = (_frameCount * 1000.f) / (currentTime - _lastTime);
            _lastTime = currentTime;
            _frameCount = 0;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        text.render(canvas, "FPS :" + (int)_fps);
    }
}
