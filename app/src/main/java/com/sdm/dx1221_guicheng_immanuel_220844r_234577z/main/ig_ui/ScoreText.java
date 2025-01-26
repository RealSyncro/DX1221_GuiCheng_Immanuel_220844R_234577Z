package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.TextUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.UIObject;

public class ScoreText extends UIObject {
    private final TextUI text;
    private int coinsCollected;
    public ScoreText(int color, int size, Paint.Align align, int xPosFlex, int yPosFlex) {
        text = new TextUI(color, size, align, xPosFlex, yPosFlex);
        coinsCollected = 0;
    }

    @Override
    public void onUpdate(float dt) {}
    @Override
    public void onRender(Canvas canvas) {
        text.render(canvas, "Score :" + coinsCollected);
    }
    public void IncrementScore(int add) {
        coinsCollected += add;
    }
    public int GetScore() {return coinsCollected;}
}
