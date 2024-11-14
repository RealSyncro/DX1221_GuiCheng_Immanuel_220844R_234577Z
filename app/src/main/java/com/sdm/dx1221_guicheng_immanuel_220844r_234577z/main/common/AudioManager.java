package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

public class AudioManager {
    private MediaPlayer _sfxPlayer;
    private MediaPlayer _bgmPlayer;
    private Vibrator _vibrator;
    private float masterBGM;
    private float masterSFX;

    public AudioManager() {
        _sfxPlayer = null;
        _bgmPlayer = null;
        _vibrator = null;

        masterBGM = 1f;
        masterSFX = 1f;
    }

    public void PlayVibration(long milliseconds, int amplitude) {
        if (_vibrator == null)
            _vibrator = (Vibrator) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        _vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, amplitude));
    }

    public void PlayBGM(int filePath) {
        if (_bgmPlayer != null)
        {
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();

            _bgmPlayer.setLooping(true);
        }

        _bgmPlayer = MediaPlayer.create(GameActivity.instance.getApplicationContext(), filePath);
        _bgmPlayer.start();
    }

    public void PlaySFX(int filePath) {
        if (_sfxPlayer != null)
        {
            if (_sfxPlayer.isPlaying())
                _sfxPlayer.stop();
        }

        _sfxPlayer = MediaPlayer.create(GameActivity.instance.getApplicationContext(), filePath);
        _sfxPlayer.start();
    }

    public void SetMasterBGM (float newVolume) {
        if (_bgmPlayer == null)
            return;

        masterBGM = newVolume;

        if (masterSFX <= 0f) masterSFX = 0f;
        else if (masterSFX >= 1f) masterSFX = 1f;

        _bgmPlayer.setVolume(masterBGM, masterBGM);
    }

    public void SetMasterSFX (float newVolume) {
        if (_sfxPlayer == null)
            return;

        masterSFX = newVolume;

        if (masterSFX <= 0f) masterSFX = 0f;
        else if (masterSFX >= 1f) masterSFX = 1f;

        _sfxPlayer.setVolume(masterSFX, masterSFX);
    }

    public void StopAllSFXPlayer() {
        if (_sfxPlayer != null)
            if (_sfxPlayer.isPlaying())
                _sfxPlayer.stop();

        if (_bgmPlayer != null)
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();
    }
}
