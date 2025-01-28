package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;
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
import java.util.Objects;
import java.util.Vector;

public class FileSystem {

    //**********************************************************************************************
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
    //**********************************************************************************************


    public static void LoadShop(String filename, Vector<Item> itemBuffer, Context m_Context) {
        List<String> _ID = new ArrayList<>();
        List<String> _ItemName = new ArrayList<>();
        List<String> _Cost = new ArrayList<>();

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
                    _Cost.add(parts[2]);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "LoadShop: ", e);
        }

        // Convert lists to arrays
        for (int i = 0; i < _ID.size(); i++)
        {
            int _tempID = Integer.parseInt(_ID.get(i));
            String _tempName = _ItemName.get(i);
            int _tempCost = Integer.parseInt(_Cost.get(i));

            Item item = new Item(_tempID, _tempName, _tempCost, 1);
            itemBuffer.add(item);
        }
    }






    //**********************************************************************************************
    public static void InitLeaderboard(String initFile, Context context) {
        List<String> displayNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open(initFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();

            while (line != null) {
                // Split the line into display name and value
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    displayNames.add(parts[0]);
                    values.add(parts[1]);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "LoadShop: ", e);
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
        Arrays.sort(indices, Comparator.comparing(index -> displayNamesArray[index]));

        // Create new arrays for sorted display names and values
        String[] sortedDisplayNames = new String[displayNamesArray.length];
        String[] sortedValues = new String[valuesArray.length];

        for (int i = 0; i < indices.length; i++) {
            sortedDisplayNames[i] = displayNamesArray[indices[i]];
            sortedValues[i] = valuesArray[indices[i]];
        }

        File file = new File(context.getExternalFilesDir(null), "leaderboard.txt");
        try {
            // Create a new file at that path
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            for (int i = 0; i < sortedDisplayNames.length; i++)
            {
                String Line = sortedDisplayNames[i] + " " + sortedValues[i] + "\n";
                writer.write(Line);
            }
            writer.close();
            Log.w("LEADERBOARD", "Initialised Leaderboard.");
        } catch (IOException e) {
            Log.e("LEADERBOARD", "InitLeaderboard failed: ", e);
        }
    }
    public static String[][] ReadLeaderboard(String filename, Context m_Context) {
        List<String> displayNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        File file = new File(m_Context.getExternalFilesDir(null), filename);
        if (!file.exists()) return null;
        try {
//            String filePath = m_Context.getFilesDir().getAbsolutePath() + filename;
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
            Log.w("LEADERBOARD", "Read LB Success!");
        } catch (IOException e) {
            Log.e("LEADERBOARD", "ReadLeaderboard: ", e);
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
        Arrays.sort(indices, Comparator.comparing(index -> displayNamesArray[index]));

        // Create new arrays for sorted display names and values
        String[] sortedDisplayNames = new String[displayNamesArray.length];
        String[] sortedValues = new String[valuesArray.length];

        for (int i = 0; i < indices.length; i++) {
            sortedDisplayNames[i] = displayNamesArray[indices[i]];
            sortedValues[i] = valuesArray[indices[i]];
        }
        return new String[][]{sortedDisplayNames, sortedValues};
    }
    public static void WriteToLeaderboard(String filename, String Line, Context m_Context) {
        File file = new File(m_Context.getExternalFilesDir(null), filename);
        try {
            // Get the path to your app's internal storage
            //String filePath = m_Context.getFilesDir().getAbsolutePath() + filename;

            // Create a new file at that path
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("\n" + Line);
            writer.close();

            Log.w("LEADERBOARD", "Write LB Success!");
        } catch (IOException e) {
            Log.e("LEADERBOARD", "WriteToLeaderboard: ", e);
        }
    }
    public static String[][] ReadStats(String filename, Context m_Context, String SearchedName) {
        List<String> displayNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        File file = new File(m_Context.getExternalFilesDir(null), filename);
        if (!file.exists()) {
            return null;
        }
        try {
//            String filePath = m_Context.getFilesDir().getAbsolutePath() + filename;
            FileInputStream InputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(InputStream));
            String line = reader.readLine();

            while (line != null) {
                // Split the line into display name and value
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    if(Objects.equals(parts[0], SearchedName)) {
                        displayNames.add(parts[0]);
                        values.add(parts[1]); // Second part as value
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "ReadLeaderboard: ", e);
        }

        // Convert lists to arrays
        String[] displayNamesArray = displayNames.toArray(new String[0]);
        String[] valuesArray = values.toArray(new String[0]);



        return new String[][]{displayNamesArray, valuesArray};
    }
}
