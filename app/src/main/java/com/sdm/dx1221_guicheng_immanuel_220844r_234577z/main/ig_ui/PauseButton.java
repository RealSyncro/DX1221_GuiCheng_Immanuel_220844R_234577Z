package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui;

import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class PauseButton extends ButtonUI {
    public PauseButton(Vector2 pos, Vector2 scale, int normalImageFilePath, int activatedImageFilePath) {
        super(pos, scale, normalImageFilePath, activatedImageFilePath);
    }

    @Override
    public void onUpdate(float dt) {
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerID = motionEvent.getPointerId(actionIndex);

        float tapX = motionEvent.getX();
        float tapY = motionEvent.getY();

        if ( (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) &&
                !BackDialog.isShowing() &&
                isPressed(tapX, tapY, 0f, pointerID)
        )
        {
            AudioController.Get().PlayVibration(100, 10);
            AudioController.Get().PlaySFX(R.raw.player_pause);
            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(), "Back dialog");
        }
    }
}