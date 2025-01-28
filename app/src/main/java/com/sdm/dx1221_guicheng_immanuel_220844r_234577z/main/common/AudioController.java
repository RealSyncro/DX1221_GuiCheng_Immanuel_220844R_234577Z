package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;

import androidx.annotation.RawRes;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class AudioController {
    private static AudioController instance = null;

    public static class SoundMap
    {
        public Vector<Integer> StreamID;
        public HashMap<Integer, Integer> SoundID;
        public SoundMap() {
            StreamID = new Vector<>();
            // Key - ResAudioID, Value - SoundID
            SoundID = new HashMap<>();
        }
    }

    private static SoundPool _sfxPlayer;
    private static SoundMap _sfxMap;
    private static MediaPlayer _bgmPlayer;
    private Vibrator _vibrator;
    private VibratorManager _vibratorManager;
    private float masterBGM, masterSFX;

    @SuppressLint("ObsoleteSdkInt")
    private AudioController(Vibrator vibrator, VibratorManager vibratorManager) {
        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        _sfxPlayer = new SoundPool.Builder().setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        _sfxMap = new SoundMap();

        _bgmPlayer = new MediaPlayer();
        _bgmPlayer.setOnPreparedListener(MediaPlayer::start);
        _bgmPlayer.setOnCompletionListener(MediaPlayer::reset);

        _vibrator = vibrator;
        _vibratorManager = vibratorManager;
        masterBGM = 1f;
        masterSFX = 1f;
    }

    // Always call this when you want to play Audio.
    public static AudioController Get(){
        if (instance == null) instance = new AudioController(null, null);
        return instance;
    }

    // Only call this once!
    public void PreloadSFX(Context c) {
        for (Integer StreamID : _sfxMap.StreamID) {
            _sfxPlayer.stop(StreamID);
        }
        for (Map.Entry<Integer, Integer> SoundID : _sfxMap.SoundID.entrySet()) {
            _sfxPlayer.unload(SoundID.getValue());
        }
        _sfxMap.SoundID.clear();
        _sfxMap.StreamID.clear();

        // Load sounds
        _sfxMap.SoundID.put(R.raw.collect_coin, _sfxPlayer.load(c, R.raw.collect_coin, 1));
        _sfxMap.SoundID.put(R.raw.firesound, _sfxPlayer.load(c, R.raw.firesound, 1));
        _sfxMap.SoundID.put(R.raw.spikesound, _sfxPlayer.load(c, R.raw.spikesound, 1));
        _sfxMap.SoundID.put(R.raw.powerupsound, _sfxPlayer.load(c, R.raw.powerupsound, 1));
        _sfxMap.SoundID.put(R.raw.player_jump, _sfxPlayer.load(c, R.raw.player_jump, 1));
        _sfxMap.SoundID.put(R.raw.player_lose, _sfxPlayer.load(c, R.raw.player_lose, 1));
        _sfxMap.SoundID.put(R.raw.player_pause, _sfxPlayer.load(c, R.raw.player_pause, 1));
        _sfxMap.SoundID.put(R.raw.button_click, _sfxPlayer.load(c, R.raw.button_click, 1));
        _sfxMap.SoundID.put(R.raw.collect_powerup, _sfxPlayer.load(c, R.raw.collect_powerup, 1));
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

    public void PlayBGM(Context context, @RawRes int resAudioID) {
        AssetFileDescriptor assetFileDescriptor = context.getResources().openRawResourceFd(resAudioID);
        if (assetFileDescriptor == null) return;

        if (_bgmPlayer != null)
        {
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();

            _bgmPlayer.reset();
            _bgmPlayer.setLooping(true);
            _bgmPlayer.setVolume(masterBGM, masterBGM);
            try {
                _bgmPlayer.setDataSource(
                        assetFileDescriptor.getFileDescriptor(),
                        assetFileDescriptor.getStartOffset(),
                        assetFileDescriptor.getDeclaredLength()
                );
                _bgmPlayer.prepare();

            } catch (Exception e) {
                Log.e("BGM PLAYER", "ERROR PLAYING: ", e);
            }
        }
    }

    public void PlaySFX(@RawRes int resAudioID) {
        if (_sfxPlayer == null) return;
        Integer iSoundID = _sfxMap.SoundID.get(resAudioID);

        if (iSoundID != null) {
            int iStreamID = _sfxPlayer.play(iSoundID, masterSFX, masterSFX, 1, 0, 1.0f);

            // Check if this StreamPlayer already exist.
            for (int existingSID : _sfxMap.StreamID)
                if (iStreamID == existingSID) return;

            // Add a new reference to StreamPlayer.
            _sfxMap.StreamID.add(iStreamID);
        }
        else
            Log.e("SFX ERROR", "Cannot Find iSoundID instance.");
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

        if (_sfxPlayer != null) {
            for (Integer iStreamID : _sfxMap.StreamID) {
                _sfxPlayer.setVolume(iStreamID, masterSFX, masterSFX);
            }
        }
        System.out.println("SFX Volume Set: " + masterSFX);
    }

    public float GetSFXVolume() {return masterSFX;}
    public float GetBGMVolume() {return  masterBGM;}

    public void ResumeAllSFXPlayer() {
        if (_sfxPlayer != null) {
            for (Integer iStreamID : _sfxMap.StreamID) {
                _sfxPlayer.resume(iStreamID);
            }
        }

        if (_bgmPlayer != null)
            _bgmPlayer.start();
    }

    public void PauseAllSFXPlayer() {
        if (_sfxPlayer != null) {
            for (Integer iStreamID : _sfxMap.StreamID) {
                _sfxPlayer.stop(iStreamID);
            }
        }

        if (_bgmPlayer != null)
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.pause();
    }

    public void StopAllSFXPlayer() {
        if (_sfxPlayer != null) {
            for (Integer iStreamID : _sfxMap.StreamID) {
                _sfxPlayer.stop(iStreamID);
            }
        }

        if (_bgmPlayer != null) {
            if (_bgmPlayer.isPlaying())
                _bgmPlayer.stop();
        }


//        if (_bgmPlayer != null)
//            _bgmPlayer.release();
//
//        _bgmPlayer = null;
    }
}
