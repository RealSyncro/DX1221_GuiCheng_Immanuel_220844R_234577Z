package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class ButtonUI {
    private final Vector2 position;
    private final Vector2 size;
    private final Bitmap sprite;

    private final float dstLeft;
    private final float dstTop;

    public ButtonUI(Vector2 pos, Vector2 scale, int fileName) {
        position = pos;
        size = scale;
        sprite = FileSystem.LoadCustomSprite(fileName, (int) scale.x, (int) scale.y, true);

        dstLeft = position.x - (float) sprite.getWidth() / 2;
        dstTop = position.y - (float) sprite.getHeight();
    }

    public void onRender(Canvas canvas) {
        canvas.drawBitmap(sprite, dstLeft, dstTop, null);
    }

    public boolean onDetect(float tapPositionX, float tapPositionY, float padding) {
        return  tapPositionX >= position.x - (size.x / 2) + padding &&
                tapPositionX <= position.x + (size.x / 2) + padding &&
                tapPositionY >= position.y - (size.y / 2) + padding &&
                tapPositionY <= position.y + (size.y / 2) + padding;
    }
}
