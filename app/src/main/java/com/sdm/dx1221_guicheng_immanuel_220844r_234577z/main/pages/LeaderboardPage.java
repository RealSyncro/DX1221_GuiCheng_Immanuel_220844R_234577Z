package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LeaderboardPage extends Activity implements View.OnClickListener {
    private Button _BackTomain;
    private TextView _Leaderboardtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);
        _BackTomain = findViewById(R.id.Leaderboard_Quit_btn);
        _Leaderboardtext =findViewById(R.id.Leaderboard);
    }
    @Override
    protected void onStart() {
        super.onStart();

        String result = readFromAssets("leaderboard.txt");
//        String[] displayNames = result[0];
//        String[] values = result[1];
//        for (int i =0; i<displayNames.length;i++){
//            String Finalstring = displayNames[i] + ": " + values[i] + "\n";
//            _Leaderboardtext.append(Finalstring);
//        }
        String Finalstring = result + "\n";
        _Leaderboardtext.append(Finalstring);
    }
    @Override
    public void onClick(View v) {
        if(v == _BackTomain){
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }
    private String readFromAssets(String filename) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream inputStream = GameActivity.instance.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ERROR", "readFromAssets: ", e);
        }
        return result.toString();
    }

    private String writeToAssets(String filename, String Line) {
        try {
            OutputStream outputStream = GameActivity.instance.openFileOutput(filename, Context.MODE_APPEND);
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
