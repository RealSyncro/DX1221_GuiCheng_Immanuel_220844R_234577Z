package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;

public class OtherPage extends Activity implements View.OnClickListener{
    private Button _inventoryButton;
    private Button _shopButton;
    private Button _profileButton;
    private Button _leaderboardButton;
    private Button _backButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.other_page);

        _inventoryButton = findViewById(R.id.o_inventory_button);
        _inventoryButton.setOnClickListener(this);

        _shopButton = findViewById(R.id.o_shop_button);
        _shopButton.setOnClickListener(this);

        _profileButton = findViewById(R.id.o_profile_button);
        _profileButton.setOnClickListener(this);

        _leaderboardButton = findViewById(R.id.o_leaderboard_button);
        _leaderboardButton.setOnClickListener(this);

        _backButton = findViewById(R.id.o_back_button);
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
        if (v == _inventoryButton)
        {
            startActivity(new Intent().setClass(this, InventoryPage.class));
        }
        else if (v == _shopButton) {
            startActivity(new Intent().setClass(this, ShopPage.class));
        }
        else if (v == _profileButton) {
            startActivity(new Intent().setClass(this, ProfilePage.class));
        }
        else if (v == _leaderboardButton) {
            startActivity(new Intent().setClass(this, LeaderboardPage.class));
        }
        else if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }
}
