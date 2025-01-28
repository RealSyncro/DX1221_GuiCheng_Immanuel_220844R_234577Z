package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.DrawableRes;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class ButtonUI {
    private static final int INVALID_POINTER = -1;
    private int _buttonPointerID = -1;

    protected final Vector2 position, size;
    protected final Bitmap inactive_bmp, active_bmp;
    protected final float dstLeft, dstTop;

    public boolean _isActive = false;

    public ButtonUI(Vector2 pos, Vector2 scale,
                    @DrawableRes int inactive_bmp,
                    @DrawableRes int active_bmp)
    {
        position = pos;
        size = scale;
        this.inactive_bmp = FileSystem.LoadCustomSprite(inactive_bmp, (int) scale.x, (int) scale.y, true);

        if (active_bmp != -1)
            this.active_bmp = FileSystem.LoadCustomSprite(active_bmp, (int) scale.x, (int) scale.y, true);
        else
            this.active_bmp = null;

        dstLeft = position.x - (float) this.inactive_bmp.getWidth() / 2f;
        dstTop = position.y - (float) this.inactive_bmp.getHeight() / 1.5f;
    }

    public void onUpdate(float dt) {}

    public void onRender(Canvas canvas) {
        if (!_isActive)
            canvas.drawBitmap(inactive_bmp, dstLeft, dstTop, null);
        else if (active_bmp != null)
            canvas.drawBitmap(active_bmp, dstLeft, dstTop, null);
    }

    public boolean isPressed(float tapPositionX, float tapPositionY, float padding, int pointerID) {
        if (tapPositionX >= position.x - (size.x / 2) + padding &&
                tapPositionX <= position.x + (size.x / 2) + padding &&
                tapPositionY >= position.y - (size.y / 2) + padding &&
                tapPositionY <= position.y + (size.y / 2) + padding)
        {
            _isActive = true;
            _buttonPointerID = pointerID;
            return true;
        }
        return false;
    }

    public boolean isDeactivated(int pointerID) {
        if (pointerID == _buttonPointerID) {
            _buttonPointerID = INVALID_POINTER;
            _isActive = false;
            return true;
        }
        return false;
    }
}
