package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class PauseButton extends ButtonUI {
    public PauseButton(Vector2 pos, Vector2 scale, int fileName) {
        super(pos, scale, fileName);
    }

    @Override
    public void onUpdate(float dt) {
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();

        if (motionEvent == null) return;

        float tapX = motionEvent.getX();
        float tapY = motionEvent.getY();

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN &&
                !BackDialog.isShowing() &&
                onDetect(tapX, tapY, 0f)
        )
        {
            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(), "Back dialog");
        }
    }
}