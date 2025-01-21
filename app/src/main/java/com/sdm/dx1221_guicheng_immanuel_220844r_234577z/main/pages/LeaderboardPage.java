package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class LeaderboardPage extends Activity implements View.OnClickListener {
    private TextView _leaderboardContent;
    private Button _BackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);

        _leaderboardContent = findViewById(R.id.leaderboard_content);

        _BackToMain = findViewById(R.id.leaderboard_back_button);
        _BackToMain.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //ERROR WHEN READING NULL REFERENCE
        String[][] result = FileSystem.readFromAssets("leaderboard.txt", this);
        String[] displayNames = result[0];
        String[] values = result[1];
        for (int i =0; i<displayNames.length;i++){
            String Finalstring = displayNames[i] + ": " + values[i] + "\n";
            _leaderboardContent.append(Finalstring);
        }
    }
    @Override
    public void onClick(View v) {
        if(v == _BackToMain)
        {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }
}