package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;

public class HelpPage extends Activity implements View.OnClickListener{

    private Button _backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
        _backButton = findViewById(R.id.game_over_back_button);
        _backButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioManager.Get().PlaySFX(this, R.raw.button_click);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }
}
