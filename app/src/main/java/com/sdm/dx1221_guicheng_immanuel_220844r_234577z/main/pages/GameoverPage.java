package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class GameoverPage extends Activity implements View.OnClickListener{
    private TextView _scoreText;
    private TextInputEditText _nameInput;
    private Button _backButton;
    private  Button _submitRun;

    private boolean Clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_page);

        _backButton = findViewById(R.id.game_over_back_button);
        _backButton.setOnClickListener(this);

        _submitRun = findViewById(R.id.game_over_SubmitRun);
        _submitRun.setOnClickListener(this);

        _nameInput = findViewById(R.id.game_over_Input);
        _scoreText = findViewById(R.id.game_over_score);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().StopAllSFXPlayer();
        AudioController.Get().PlaySFX(R.raw.player_lose);

        // Update player score
         CharSequence score = String.valueOf(SaveSystem.Get().GetScore());
        _scoreText.append(" " + score);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
        if(v == _submitRun){
            if(Clicked){
                _nameInput.setEnabled(false);
                _submitRun.setEnabled(false);
            }
            if(_nameInput != null){
                Clicked = true;
                StringBuilder currStringAppended = new StringBuilder();
                String[][] result = FileSystem.ReadLeaderboard("leaderboard.txt", this);

                if (result != null) {
                    String[] displayNames = result[0];
                    String[] values = result[1];
                    for (int i = 0; i < displayNames.length; i++) {
                        String Finalstring = displayNames[i] + " " + values[i] + "\n";
                        currStringAppended.append(Finalstring);
                    }
                }

                String finalstring = currStringAppended.toString() + _nameInput.getText() + " " + SaveSystem.Get().GetScore();
                FileSystem.WriteToLeaderboard("leaderboard.txt", finalstring, this);
            }
        }
    }
    public void SetNameFalse(){
        Clicked = true;
    }
}
