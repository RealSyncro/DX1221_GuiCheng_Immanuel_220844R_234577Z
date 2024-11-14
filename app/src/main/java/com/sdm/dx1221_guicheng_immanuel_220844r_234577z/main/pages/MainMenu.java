package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.MainGameScene;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameScene;

public class MainMenu extends Activity implements View.OnClickListener{

    private Button _helpButton;
    private  Button _startButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main_menu);
        _helpButton = findViewById(R.id.help_button);
        _helpButton.setOnClickListener(this);
        _startButton = findViewById(R.id.start_button);
        _startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == _helpButton) {
            //Intent intent = new Intent();
            //intent.setClass(this, HelpPage.class);
            startActivity(new Intent().setClass(this, HelpPage.class));
        }
        else if (v == _startButton)
        {
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
        }
    }
}
