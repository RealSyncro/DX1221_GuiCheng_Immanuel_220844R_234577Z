package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;

public class GameoverPage extends Activity implements View.OnClickListener{
    private TextView _textView;
    private Button _backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_page);
        _backButton = findViewById(R.id.game_over_back_button);
        _backButton.setOnClickListener(this);

        _textView = findViewById(R.id.game_over_score);
    }

    @Override
    protected void onStart() {
        super.onStart();
         CharSequence score = String.valueOf(SaveSystem.Get().GetScore());
        _textView.append(" " + score);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }
}
