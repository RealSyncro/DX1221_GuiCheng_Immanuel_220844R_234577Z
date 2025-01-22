package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

        File file = new File(m_Context.getExternalFilesDir(null), filename);
        if (!file.exists()) {
            return null;
        }
        try {
            String filePath = m_Context.getFilesDir().getAbsolutePath() + filename;
            FileInputStream InputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(InputStream));
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

        // Sort displayNamesArray and keep track of original indices
        Integer[] indices = new Integer[displayNamesArray.length];
        for (int i = 0; i < displayNamesArray.length; i++) {
            indices[i] = i; // Initialize indices array
        }

        // Sort the indices based on the display names
        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer index1, Integer index2) {
                return displayNamesArray[index1].compareTo(displayNamesArray[index2]);
            }
        });

        // Create new arrays for sorted display names and values
        String[] sortedDisplayNames = new String[displayNamesArray.length];
        String[] sortedValues = new String[valuesArray.length];

        for (int i = 0; i < indices.length; i++) {
            sortedDisplayNames[i] = displayNamesArray[indices[i]];
            sortedValues[i] = valuesArray[indices[i]];
        }

        return new String[][]{sortedDisplayNames, sortedValues};
    }

    public static void writeToAssets(String filename, String Line, Context m_Context) {

        File file = new File(m_Context.getExternalFilesDir(null), filename);

        try {
            // Get the path to your app's internal storage
            //String filePath = m_Context.getFilesDir().getAbsolutePath() + filename;

            // Create a new file at that path
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            // Write some data to the file
            writer.write("\n" + Line);

        // Don't forget to close the writer when you're done!
            writer.close();
        } catch (IOException e) {
            Log.e("ERROR", "WriteToAsset: ", e);
        }
    }
}
