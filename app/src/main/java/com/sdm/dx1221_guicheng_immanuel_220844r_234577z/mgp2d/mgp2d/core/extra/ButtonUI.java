package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.Vector2;

public class ButtonUI {
    private final Vector2 position, size;
    private final Bitmap _normalButton, _activeButton;
    private final float dstLeft, dstTop;
    public int _buttonPointerID = -1;
    public boolean _isActive = false;
    private static final int INVALID_POINTER = -1;

    // normalImageFilePath -> inactive_button image file
    // activatedImageFilePath -> active_button image file
    public ButtonUI(Vector2 pos, Vector2 scale, int normalImageFilePath, int activatedImageFilePath) {
        position = pos;
        size = scale;
        _normalButton = FileSystem.LoadCustomSprite(normalImageFilePath, (int) scale.x, (int) scale.y, true);

        if (activatedImageFilePath != -1)
            _activeButton = FileSystem.LoadCustomSprite(activatedImageFilePath, (int) scale.x, (int) scale.y, true);
        else
            _activeButton = null;

        dstLeft = position.x - (float) _normalButton.getWidth() / 2f;
        dstTop = position.y - (float) _normalButton.getHeight() / 1.5f;
    }

    public void onUpdate(float dt) {}

    public void onRender(Canvas canvas) {
        if (!_isActive)
            canvas.drawBitmap(_normalButton, dstLeft, dstTop, null);
        else if (_activeButton != null)
            canvas.drawBitmap(_activeButton, dstLeft, dstTop, null);
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
