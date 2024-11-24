package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.MainGameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameScene;

public class MainMenu extends Activity implements View.OnClickListener{

    private  Button _startButton;
    private Button _settingsButton;
    private Button _helpButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main_menu);

        _startButton = findViewById(R.id.start_button);
        _startButton.setOnClickListener(this);

        _settingsButton = findViewById(R.id.settings_button);
        _settingsButton.setOnClickListener(this);

        _helpButton = findViewById(R.id.help_button);
        _helpButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioManager.Get().PlayBGM(this, R.raw.shinytech);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioManager.Get().ResumeAllSFXPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioManager.Get().PauseAllSFXPlayer();
    }

    @Override
    public void onClick(View v) {
        if (v == _startButton)
        {
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
        }
        else if (v == _settingsButton) {
            startActivity(new Intent().setClass(this, Settings.class));
        }
        else if (v == _helpButton) {
            startActivity(new Intent().setClass(this, HelpPage.class));
        }
    }
}
