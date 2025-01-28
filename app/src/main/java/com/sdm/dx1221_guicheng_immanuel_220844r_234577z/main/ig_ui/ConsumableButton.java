package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ig_ui;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.PlayerObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.ButtonUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.TextUI;

public class ConsumableButton extends ButtonUI {
    private final PlayerObject _player;
    private final Item _consumable;
    private final TextUI _quantityText;

    public ConsumableButton(Vector2 pos, Vector2 scale,
                            Item current, PlayerObject player)
    {
        super(pos, scale, current.InActiveID, current.ActiveID);
        _player = player;
        _consumable = current;
        _quantityText = new TextUI(Color.WHITE, 60, Paint.Align.CENTER, 98, 43);
    }

    @Override
    public void onUpdate(float dt) {
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;
        if (_consumable.quantity <= 0) return;
        if (_player._consumableCD > 0f) return;

        int action = motionEvent.getActionMasked();
        if ((action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN) && _isActive)
        {
            AudioController.Get().PlaySFX(R.raw.collect_powerup);

            // Item function here.
            _consumable.quantity -= 1;
            _player.ConsumeItem(_consumable);
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        if (!_isActive && _player._consumableCD <= 0f)
        {
            canvas.drawBitmap(inactive_bmp, dstLeft, dstTop, null);
        }
        else if (active_bmp != null && _player._consumableCD > 0f
                || active_bmp != null && _consumable.quantity <= 0f)
        {
            canvas.drawBitmap(active_bmp, dstLeft, dstTop, null);
        }

        _quantityText.render(canvas, "x" + _consumable.quantity);
    }
}