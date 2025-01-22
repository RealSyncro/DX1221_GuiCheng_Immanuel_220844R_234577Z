package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.Item;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
    public static String[][] readFromAssets(String filename, Context m_Context) {
        List<String> displayNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        try {
            FileInputStream fis = m_Context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
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

    public static void LoadItemAssets(String filename, Vector<Item> itemBuffer, Context m_Context) {
        List<String> _ID = new ArrayList<>();
        List<String> _ItemName = new ArrayList<>();
        List<String> _cost = new ArrayList<>();

        try {
            InputStream is = m_Context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();

            while (line != null) {
                // Split the line into display name and value
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    _ID.add(parts[0]);
                    _ItemName.add(parts[1]);
                    _cost.add(parts[2]);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "LoadItemAssets: ", e);
        }

        // Convert lists to arrays
        for (int i = 0; i < _ID.size(); i++)
        {
            int _tempID = Integer.parseInt(_ID.get(i));
            String _tempName = _ItemName.get(i);
            int _tempCost = Integer.parseInt(_cost.get(i));

            Item item = new Item(_tempID, _tempName, _tempCost);
            itemBuffer.add(item);
        }
    }


    public static void writeToAssets(String filename, String Line, Context m_Context) {
        try {
            Log.e("WRITING", "Trying to write:");
            OutputStream outputStream = m_Context.openFileOutput(filename, Context.MODE_APPEND);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            // Update player score
            CharSequence serializeLine = String.valueOf(Line);

            writer.newLine();
            writer.append(serializeLine);
            writer.close();
        } catch (IOException e) {
            Log.e("ERROR", "WriteToAsset: ", e);
        }
    }
}
