package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.DrawableRes;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.PlayerObject;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class Buff {
    private final int ID;
    private final PlayerObject reference;
    private final AnimatedSprite _animatedSprite;
    private float duration;

    public boolean _isActive;

    public Buff(PlayerObject player, float duration, int ID, @DrawableRes int res)
    {
        reference = player;
        this.duration = duration;
        this.ID = ID;

        Bitmap _sprite = FileSystem.LoadScaledSprite(res, 0.5f, 0.5f, true);
        _animatedSprite = new AnimatedSprite(_sprite, 1,  1, 1);

        _isActive = true;
    }

    public void onUpdate(float dt)
    {
        if (duration > 0f) duration -= dt;
        else {_isActive = false;}
    }
    public void onRender(Canvas canvas)
    {
        _animatedSprite.onRender(canvas, (int) reference.rigidbody._position.x, (int) reference.rigidbody._position.y, null);
    }

    public int GetID() {return ID;}
}
