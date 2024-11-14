package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

public class FileSystem {

    // Load Sprite using pre-existing aspect ratio and scaling it.
    public static Bitmap LoadScaledSprite(int filePath, float xScale, float yScale, boolean filter) {
        Bitmap original = BitmapFactory.decodeResource(GameActivity.instance.getResources(), filePath);
        return Bitmap.createScaledBitmap(original, (int) (original.getWidth() * xScale), (int) (original.getHeight() * yScale), filter);
    }

    // Load Sprite with specified x width and y height.
    public static Bitmap LoadCustomSprite(int filePath, int xWidth, int yHeight, boolean filter) {
        Bitmap original = BitmapFactory.decodeResource(GameActivity.instance.getResources(), filePath);
        return Bitmap.createScaledBitmap(original, xWidth, yHeight, filter);
    }
}
