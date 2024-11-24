package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

public class TextUI {
    public final int _screenWidth;
    public final int _screenHeight;
    public final Paint _paint;

    // TextUI Constructor:
    // color - Color.rgb()
    // size - 1 - 100
    // Paint.Align - Paint.Align.LEFT / CENTER / RIGHT
    // xPosFlex - Relative to screen (1 - 100) scale
    // yPosFlex - Relative to screen (1 - 100) scale
    public TextUI(int color, int size, Paint.Align align, int xPosFlex, int yPosFlex) {
        _paint = new Paint();
        _paint.setColor(color);
        _paint.setTextSize(size);
        _paint.setTextAlign(align);

        _screenWidth = (GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 100) * xPosFlex;
        _screenHeight = (GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 100) * yPosFlex;
    }

    public void render(Canvas canvas, String message) {
        canvas.drawText(message, _screenWidth, _screenHeight, _paint);
    }
}
