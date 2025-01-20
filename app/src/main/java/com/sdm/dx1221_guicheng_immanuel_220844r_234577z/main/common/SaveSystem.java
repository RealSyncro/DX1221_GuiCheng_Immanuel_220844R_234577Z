package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveSystem {
    private static SaveSystem instance = null;
    private String _playerName;
    private int _coins;
    private int _score;
    private int _highScore;

    private SaveSystem() {
        _playerName = "null";
        _coins = -1;
        _score = -1;
        _highScore = -1;
    }

    // Always Call this when you want to save/retrieve player data.
    public static SaveSystem Get() {
        if (instance == null) instance = new SaveSystem();
        return instance;
    }

    public void InitShared(SharedPreferences _sharedData) {
        if (_sharedData != null) {
            _playerName = _sharedData.getString("playerName", "null");
            _score = _sharedData.getInt("score", -1);

            if (_playerName.equals("null") && _score == -1) {
                Editor editor = _sharedData.edit();
                editor.putString("playerName", "Default");
                editor.putInt("coins", 0);
                editor.putInt("score", 0);
                editor.putInt("highScore", 0);
                editor.apply();
            }
        }
    }

    public void UpdateSave(SharedPreferences _sharedData, String playerName, int score) {
        _playerName = playerName;
        _score = score;

        if (_score > _highScore)
            _highScore = score;

        Editor editor = _sharedData.edit();
        editor.putString("playerName", _playerName);

        _coins = _sharedData.getInt("coins", -1);
        _coins += score;
        editor.putInt("coins", _coins);

        editor.putInt("score", _score);
        editor.putInt("highScore", _highScore);
        editor.apply();
    }

    public String GetName() {return _playerName;}
    public int GetScore() {return _score;}
    public int GetHighScore() {return _highScore;}
}
