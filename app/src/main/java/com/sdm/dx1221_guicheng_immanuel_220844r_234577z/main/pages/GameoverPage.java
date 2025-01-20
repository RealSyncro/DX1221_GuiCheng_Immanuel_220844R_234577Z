package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.color.utilities.Score;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;

public class GameoverPage extends Activity implements View.OnClickListener{
    private TextView _textView;
    private Button _backButton;

    private  Button _SubmitRun;
    private TextInputEditText _NameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_page);
        _backButton = findViewById(R.id.game_over_back_button);
        _backButton.setOnClickListener(this);
        _SubmitRun = findViewById(R.id.game_over_SubmitRun);
        _NameInput = findViewById(R.id.game_over_Input);
        _textView = findViewById(R.id.game_over_score);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Update player score
         CharSequence score = String.valueOf(SaveSystem.Get().GetScore());
        _textView.append(" " + score);
        AudioManager.Get().PlaySFX(GameActivity.instance, R.raw.player_lose);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
        if(v == _SubmitRun){
            if(_NameInput != null){
                String finalstring = _NameInput.toString() + " " + String.valueOf(SaveSystem.Get().GetScore());
                //FileSystem.writeToAssets("leaderboard.txt", finalstring);
            }
        }
    }
}
