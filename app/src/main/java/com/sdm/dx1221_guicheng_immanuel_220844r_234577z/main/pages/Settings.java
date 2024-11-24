package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;

public class Settings extends Activity implements View.OnClickListener{
    private Button _backButton;
    private SeekBar _masterSFXSlider;
    private SeekBar _masterBGMSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        _backButton = findViewById(R.id.settings_back);
        _backButton.setOnClickListener(this);

        _masterSFXSlider = findViewById(R.id.master_sfx_slider);
        _masterSFXSlider.setProgress((int)AudioManager.Get().GetSFXVolume() * 100);

        _masterBGMSlider = findViewById(R.id.master_bgm_slider);
        _masterBGMSlider.setProgress((int)AudioManager.Get().GetBGMVolume() * 100);
        onCreateButtonListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        int sfxVolume = (int) (AudioManager.Get().GetSFXVolume() * 100);
        _masterSFXSlider.setProgress(sfxVolume);

        int sfxBgm = (int) (AudioManager.Get().GetBGMVolume() * 100);
        _masterBGMSlider.setProgress(sfxBgm);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }

    private void onCreateButtonListeners() {

        _masterSFXSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // update settings
                AudioManager.Get().SetMasterSFX(progress * 0.01f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        _masterBGMSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // update settings
                AudioManager.Get().SetMasterBGM(progress * 0.01f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
