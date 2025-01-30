package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class ProfilePage extends Activity implements View.OnClickListener {
    private TextView _leaderboardContent;
    private TextInputEditText NameInput;
    private String NameSearched;

    private Button _SearchBtn;
    private  Button _BackToMain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);

        _leaderboardContent = findViewById(R.id.leaderboard_Txt);

        NameInput = findViewById(R.id.Name_Input);

        _BackToMain = findViewById(R.id.back_btn);
        _BackToMain.setOnClickListener(this);

        _SearchBtn = findViewById(R.id.search_btn);
        _SearchBtn.setOnClickListener(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onClick(View v) {
        if(v == _BackToMain)
        {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
        if(v == _SearchBtn){
            NameSearched = NameInput.getText().toString();
            SearchingName();
        }
    }
    private void SearchingName(){
        //ERROR WHEN READING NULL REFERENCE
        String[][] result = FileSystem.ReadStats("leaderboard.txt", this, NameSearched);


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
