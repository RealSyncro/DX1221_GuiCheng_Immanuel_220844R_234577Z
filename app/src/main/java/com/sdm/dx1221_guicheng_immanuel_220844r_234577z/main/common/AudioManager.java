package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

public class AudioManager {
    private static AudioManager instance = null;
    private MediaPlayer _sfxPlayer;
    private MediaPlayer _bgmPlayer;
    private Vibrator _vibrator;
    private VibratorManager _vibratorManager;
    private float masterBGM;
    private float masterSFX;

    private AudioManager(Vibrator vibrator, VibratorManager vibratorManager) {
        _sfxPlayer = null;
        _bgmPlayer = null;
        _vibrator = vibrator;
        _vibratorManager = vibratorManager;
        masterBGM = 1f;
        masterSFX = 1f;
    }

    // Always call this when you want to play Audio.
    public static AudioManager Get(){
        if (instance == null) instance = new AudioManager(null, null);
        return instance;
    }

    public void EnableVibration(Vibrator vibrator, VibratorManager vibratorManager) {
        _vibrator = vibrator;
        _vibratorManager = vibratorManager;
    }

    public void PlayVibration(long milliseconds, int amplitude) {
        if (_vibrator != null) {
            _vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, amplitude));
            return;
        }

        if (_vibratorManager != null) {
            _vibratorManager.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(milliseconds, amplitude)));
        }
    }

    // context - the class you are calling this function in.
    public void PlayBGM(Context context, int filePath) {
        if (_bgmPlayer != null)
        {
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();
        }

//        _bgmPlayer = MediaPlayer.create(GameActivity.instance.getApplicationContext(), filePath);
        _bgmPlayer = MediaPlayer.create(context.getApplicationContext(), filePath);
        _bgmPlayer.setLooping(true);
        _bgmPlayer.setVolume(masterBGM, masterBGM);
        _bgmPlayer.start();
    }

    public void PlaySFX(Context context, int filePath) {
        _sfxPlayer = MediaPlayer.create(context.getApplicationContext(), filePath);
        _sfxPlayer.setVolume(masterSFX, masterSFX);
        _sfxPlayer.start();
    }

    public void SetMasterBGM (float newVolume) {
        masterBGM = newVolume;

        if (masterBGM <= 0f) masterBGM = 0f;
        else if (masterBGM >= 1f) masterBGM = 1f;

        if (_bgmPlayer != null)
            _bgmPlayer.setVolume(masterBGM, masterBGM);

        System.out.println("BGM Volume Set: " + masterBGM);
    }

    public void SetMasterSFX (float newVolume) {
        masterSFX = newVolume;

        if (masterSFX <= 0f) masterSFX = 0f;
        else if (masterSFX >= 1f) masterSFX = 1f;

        if (_sfxPlayer != null)
            _sfxPlayer.setVolume(masterSFX, masterSFX);

        System.out.println("SFX Volume Set: " + masterSFX);
    }

    public float GetSFXVolume() {return masterSFX;}
    public float GetBGMVolume() {return  masterBGM;}

    public void ResumeAllSFXPlayer() {
        if (_sfxPlayer != null)
            _sfxPlayer.start();

        if (_bgmPlayer != null)
            _bgmPlayer.start();
    }

    public void PauseAllSFXPlayer() {
        if (_sfxPlayer != null)
            if (_sfxPlayer.isPlaying())
                _sfxPlayer.pause();

        if (_bgmPlayer != null)
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.pause();
    }

    private void StopAllSFXPlayer() {
        if (_sfxPlayer != null)
            if (_sfxPlayer.isPlaying())
                _sfxPlayer.stop();

        if (_bgmPlayer != null)
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();
    }

    public void TerminateSFXPlayer() {
        StopAllSFXPlayer();

        if (_sfxPlayer != null) {
            _sfxPlayer.release();
            _sfxPlayer = null;
        }

        if (_bgmPlayer != null) {
            _bgmPlayer.release();
            _bgmPlayer = null;
        }
    }
}
