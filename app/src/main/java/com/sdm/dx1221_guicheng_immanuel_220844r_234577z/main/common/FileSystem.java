package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.MainGameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileSystem {

    private Context context;

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
    public static String[][] readFromAssets(String filename) {
        List<String> displayNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        try {
            InputStream inputStream = GameActivity.instance.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();

            while (line != null) {
                // Split the line into display name and value
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    displayNames.add(parts[0]); // First part as display name
                    values.add(parts[1]); // Second part as value
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "readFromAssets: ", e);
        }

        // Convert lists to arrays
        String[] displayNamesArray = displayNames.toArray(new String[0]);
        String[] valuesArray = values.toArray(new String[0]);

        return new String[][]{displayNamesArray, valuesArray};
    }

    public static String writeToAssets(String filename, String Line) {
        try {
            OutputStream outputStream = GameActivity.instance.openFileOutput(filename,Context.MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.newLine();
            writer.write(Line);
            writer.close();
        } catch (IOException e) {
            Log.e("ERROR", "WriteToAsset: ", e);
        }
        return Line;
    }
}
