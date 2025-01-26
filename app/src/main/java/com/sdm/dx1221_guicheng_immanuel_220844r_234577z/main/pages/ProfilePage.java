package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;

public class ProfilePage extends Activity implements View.OnClickListener{
    private Button _searchButton;
    private Button _backButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.profile_page);

        _searchButton = findViewById(R.id.profile_search_button);
        _searchButton.setOnClickListener(this);

        _backButton = findViewById(R.id.profile_back_button);
        _backButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        if (v == _searchButton) {
            Log.w("PROFILE_PAGE", "Search Button Function");
        }
        else if (v == _backButton) {
            startActivity(new Intent().setClass(this, OtherPage.class));
        }
    }
}
