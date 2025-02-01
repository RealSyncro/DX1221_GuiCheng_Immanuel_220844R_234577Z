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
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class ProfilePage extends Activity implements View.OnClickListener {
    private TextView _leaderboardContent;
    private TextInputEditText _nameInput;
    private String nameSearched;

    private Button _searchButton;
    private Button _backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        _leaderboardContent = findViewById(R.id.profile_leaderboard_content);

        _nameInput = findViewById(R.id.name_input);

        _searchButton = findViewById(R.id.profile_search_button);
        _searchButton.setOnClickListener(this);

        _backButton = findViewById(R.id.profile_back_button);
        _backButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().PlayBGM(this, R.raw.generic_music);
        AudioController.Get().PlaySFX(R.raw.button_click);
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
        if(v == _searchButton){
            if (_nameInput.getText() != null) {
                nameSearched = _nameInput.getText().toString();
                SearchingName();
            }
        }
        else if(v == _backButton) {
            startActivity(new Intent().setClass(this, OtherPage.class));
        }
    }

    private void SearchingName(){
        //ERROR WHEN READING NULL REFERENCE
        String[][] result = FileSystem.ReadStats("leaderboard.txt", this, nameSearched);

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
}
