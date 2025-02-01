package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;

public class Settings extends Activity implements View.OnClickListener{

    private SeekBar _masterSFXSlider;
    private SeekBar _masterBGMSlider;
    private CheckBox _enableAccelerometer;
    private Button _backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        _masterSFXSlider = findViewById(R.id.master_sfx_slider);
        _masterSFXSlider.setProgress((int) AudioController.Get().GetSFXVolume() * 100);

        _masterBGMSlider = findViewById(R.id.master_bgm_slider);
        _masterBGMSlider.setProgress((int) AudioController.Get().GetBGMVolume() * 100);

        _enableAccelerometer = findViewById(R.id.settings_accelerometer_button);

        // If accelerometer is checked before
        if (SaveSystem.Get().GetAccelSetting())
            _enableAccelerometer.setChecked(true);

        _enableAccelerometer.setOnCheckedChangeListener((buttonView, isChecked) -> OnCheckedAccelerometer());

        _backButton = findViewById(R.id.game_over_back_button);
        _backButton.setOnClickListener(this);

        onCreateButtonListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().StopAllSFXPlayer();
        AudioController.Get().PlayBGM(this, R.raw.generic_music);
        AudioController.Get().PlaySFX(R.raw.button_click);

        // Update the slider to current BGM/SFX volume whenever user enters the page.
        // This is done to avoid confusion from user.
        int sfxVolume = (int) (AudioController.Get().GetSFXVolume() * 100);
        _masterSFXSlider.setProgress(sfxVolume);

        int sfxBgm = (int) (AudioController.Get().GetBGMVolume() * 100);
        _masterBGMSlider.setProgress(sfxBgm);
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
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }
    }

    private void onCreateButtonListeners() {

        _masterSFXSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update audio settings.
                AudioController.Get().SetMasterSFX(progress * 0.01f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        _masterBGMSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update audio settings.
                AudioController.Get().SetMasterBGM(progress * 0.01f);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void OnCheckedAccelerometer() {
        boolean status = SaveSystem.Get().GetAccelSetting();
        SaveSystem.Get().SetAccelSetting(!status);

        if (SaveSystem.Get().GetAccelSetting())
            Toast.makeText(this, "You have enabled accelerometer!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "You have disabled accelerometer!", Toast.LENGTH_SHORT).show();
    }
}
