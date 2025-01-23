package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.Item;

import java.util.Vector;

public class SaveSystem {
    private static SaveSystem instance = null;
    private String _playerName;
    private int _coins;
    private int _score;
    private int _highScore;
    private SharedPreferences _sharedPreferences;
    private final Vector<Item> _inventory;

    private SaveSystem() {
        _playerName = "null";
        _coins = -1;
        _score = -1;
        _highScore = -1;

        _inventory = new Vector<>();
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

    public void UpdateSave(String playerName, int score, int coins) {
        _playerName = playerName;
        _score = score;
        _coins = coins;

        if (_score > _highScore)
            _highScore = score;

        if (_sharedPreferences != null) {
            Editor editor = _sharedPreferences.edit();
            editor.putString("playerName", _playerName);

            _coins = _sharedPreferences.getInt("coins", -1);
            _coins += score;
            editor.putInt("coins", _coins);

            editor.putInt("score", _score);
            editor.putInt("highScore", _highScore);
            editor.apply();
        }
    }

    public void AddItem(Item Bought) {
        if (!_inventory.isEmpty())
        {
            for (int i = 0; i < _inventory.size(); i++)
            {
                if (Bought.ID == _inventory.get(i).ID)
                {
                    _inventory.get(i).quantity += 1;
                    return;
                }
            }
        }

        Item item = new Item(Bought);
        _inventory.add(item);
    }

    public String GetName() {return _playerName;}
    public int GetScore() {return _score;}
    public int GetHighScore() {return _highScore;}
    public int GetCoins() {return  _coins;}
    public void SetCoins(int value) { _coins = value;}
    public Vector<Item> GetInventory() {return _inventory;}
}
