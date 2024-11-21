package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class InputController {
    // UI Interface for Input Buttons

    // Button Types:
    // Normal ButtonUI
    // PauseButton
    public final PauseButton pauseButton;
    public final ButtonUI leftArrow, rightArrow;
    public final ButtonUI jumpButton, debugButton;

    public InputController() {
        float screenRatio = 20;
        float screenX = GameActivity.instance.getResources().getDisplayMetrics().widthPixels / screenRatio;
        float screenY = GameActivity.instance.getResources().getDisplayMetrics().heightPixels / screenRatio;

        Vector2 _buttonSize = new Vector2(screenX * 5f, screenX * 5f);

        // Set-up position of button relative to screen.
        Vector2 PAUSE_POS = new Vector2(screenX * 3, screenY * 2);
        Vector2 LA_POS = new Vector2(screenX * 3, screenY * 18);
        Vector2 RA_POS = new Vector2(screenX * 10, screenY * 18);
        Vector2 J_POS = new Vector2(screenX * 16, screenY * 18);
        Vector2 DEBUG_POS = new Vector2(screenX * 16, screenY * 12);

        pauseButton = new PauseButton(PAUSE_POS, new Vector2(screenX * 4f, screenX * 4f), R.drawable.pause);
        leftArrow = new ButtonUI(LA_POS, _buttonSize, R.drawable.left_arrow);
        rightArrow = new ButtonUI(RA_POS, _buttonSize, R.drawable.right_arrow);
        jumpButton = new ButtonUI(J_POS, _buttonSize, R.drawable.jump);
        debugButton = new ButtonUI(DEBUG_POS, _buttonSize, R.drawable.jump);
    }

    public void OnUpdate(float dt) {
        pauseButton.onUpdate(dt);
    }

    public void onRender(Canvas canvas) {
        pauseButton.onRender(canvas);
        leftArrow.onRender(canvas);
        rightArrow.onRender(canvas);
        jumpButton.onRender(canvas);
        debugButton.onRender(canvas);
    }
}
