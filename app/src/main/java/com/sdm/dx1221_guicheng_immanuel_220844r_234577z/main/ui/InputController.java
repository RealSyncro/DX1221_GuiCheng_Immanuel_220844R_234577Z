package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class InputController {
    public final ButtonUI leftArrow;
    public final ButtonUI rightArrow;
    public final ButtonUI jumpButton;

    public InputController() {
        float screenX = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        float screenY = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        Vector2 _buttonSize = new Vector2(150f, 150f);

        // Set-up position of button relative to screen.
        Vector2 LA_POS = new Vector2((screenX / 16) * 2, screenY - 100f);
        Vector2 RA_POS = new Vector2((screenX / 16) * 6, screenY - 100f);
        Vector2 J_POS = new Vector2((screenX / 16) * 12, screenY - 100f);

        leftArrow = new ButtonUI(LA_POS, _buttonSize, R.drawable.pause);
        rightArrow = new ButtonUI(RA_POS, _buttonSize, R.drawable.pause);
        jumpButton = new ButtonUI(J_POS, _buttonSize, R.drawable.pause);
    }

    public void onRender(Canvas canvas) {
        leftArrow.onRender(canvas);
        rightArrow.onRender(canvas);
        jumpButton.onRender(canvas);
    }
}
