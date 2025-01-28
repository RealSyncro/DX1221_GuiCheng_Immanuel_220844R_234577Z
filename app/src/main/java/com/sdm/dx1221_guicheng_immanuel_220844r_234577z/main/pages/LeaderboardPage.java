package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class LeaderboardPage extends Activity implements View.OnClickListener {
    private TextView _leaderboardContent;
    private Button _BackToOther;

    private Button _SortButton;
    private  boolean SortByNum = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);
        AudioController.Get().StopAllSFXPlayer();

        _leaderboardContent = findViewById(R.id.leaderboard_content);

        _BackToOther = findViewById(R.id.leaderboard_back_button);
        _BackToOther.setOnClickListener(this);

        _SortButton = findViewById(R.id.leaderboardsort_btn);
        _SortButton.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().StopAllSFXPlayer();
        AudioController.Get().PlayBGM(this, R.raw.generic_music);
        AudioController.Get().PlaySFX(R.raw.button_click);

        //ERROR WHEN READING NULL REFERENCE
        String[][] result = FileSystem.ReadNumLeaderboard(getApplicationContext());
        SortByNum = true;

        if (result == null) {
            Log.d("FileSystem", "File was not found, creating new file...");
            return;
        }


        String[] displayNames = result[0];
        String[] values = result[1];

        for (int i = 0; i < displayNames.length; i++){
            String Finalstring = (i + 1) + ". " + displayNames[i] + ": " + values[i] + "\n";
            _leaderboardContent.append(Finalstring);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioController.Get().ResumeAllSFXPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioController.Get().PauseAllSFXPlayer();
    }

    @Override
    public void onClick(View v) {
        if(v == _BackToOther)
        {
            startActivity(new Intent().setClass(this, OtherPage.class));
        }
        else if(v == _SortButton){
            _leaderboardContent.setText("");
            if(!SortByNum) {
                String[][] result = FileSystem.ReadNumLeaderboard(getApplicationContext());
                SortByNum = true;

                if (result == null) {
                    //FileSystem.writeToAssets("leaderboard.txt", "testing test", this);
                    //FileSystem.Readwrite("leaderboard.txt", this);
                    Log.d("FileSystem", "File was not found, creating new file...");
                    return;
                }


                String[] displayNames = result[0];
                String[] values = result[1];

                for (int i = 0; i < displayNames.length; i++){
                    String Finalstring = (i + 1) + ". " + displayNames[i] + ": " + values[i] + "\n";
                    _leaderboardContent.append(Finalstring);
                }
            }
            else {
                String[][] result = FileSystem.ReadNameLeaderboard(getApplicationContext());
                SortByNum = false;

                if (result == null) {
                    //FileSystem.writeToAssets("leaderboard.txt", "testing test", this);
                    //FileSystem.Readwrite("leaderboard.txt", this);
                    Log.d("FileSystem", "File was not found, creating new file...");
                    return;
                }


                String[] displayNames = result[0];
                String[] values = result[1];

                for (int i = 0; i < displayNames.length; i++){
                    String Finalstring = (i + 1) + ". " + displayNames[i] + ": " + values[i] + "\n";
                    _leaderboardContent.append(Finalstring);
                }
            }
        }
    }
}
