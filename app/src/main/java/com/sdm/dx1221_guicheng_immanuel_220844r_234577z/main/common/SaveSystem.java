package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;

public class SaveSystem {
    private static SaveSystem instance = null;
    private String _playerName;
    private int _score;

    private SaveSystem() {
        _playerName = "null";
        _score = 0;
    }

    // Always Call this when you want to save/retrieve player data.
    public static SaveSystem Get() {
        if (instance == null) instance = new SaveSystem();
        return instance;
    }

    public void UpdateSave(String playerName, int score) {
        _playerName = playerName;
        _score = score;
    }

    public String GetName() {return _playerName;}
    public int GetScore() {return _score;}
}
