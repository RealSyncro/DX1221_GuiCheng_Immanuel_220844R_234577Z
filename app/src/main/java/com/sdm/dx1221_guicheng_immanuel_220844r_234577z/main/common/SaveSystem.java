package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SaveSystem {
    private static SaveSystem instance = null;
    private String _playerName;
    private int _coins, _score, _highScore;
    private boolean _firstInit;
    private SharedPreferences _sharedPreferences;
    private Item _currentItem;
    private final Vector<Item> _inventory;

    private SaveSystem() {
        _playerName = "null";
        _coins = -1;
        _score = -1;
        _highScore = -1;
        _firstInit = true;
        _sharedPreferences = null;

        _currentItem = null;
        _inventory = new Vector<>();
    }

    // Always Call this when you want to save/retrieve player data.
    public static SaveSystem Get() {
        if (instance == null) instance = new SaveSystem();
        return instance;
    }

    public void InitShared(SharedPreferences _sharedData) {
        _sharedPreferences = _sharedData;

        if (_sharedData != null) {
            _playerName = _sharedData.getString("playerName", "null");
            _score = _sharedData.getInt("score", -1);
            _coins = _sharedData.getInt("coins", 0);
            _highScore = _sharedData.getInt("highScore", 0);

            if (_playerName.equals("null") && _score == -1) {
                Editor editor = _sharedData.edit();
                editor.putString("playerName", "Default");
                editor.putInt("coins", 0);
                editor.putInt("score", 0);
                editor.putInt("highScore", 0);
                editor.putBoolean("firstInit", true);
                editor.apply();
            }
            else {
                _firstInit = false;
                Editor editor = _sharedData.edit();
                editor.putBoolean("firstInit", _firstInit);
                editor.apply();
            }
        }
    }

    public void UpdateSave(String playerName, int score, int coins, Context c) {
        _playerName = playerName;
        _score = score;
        _coins = coins;

        if (_score > _highScore)
            _highScore = score;

        if (_sharedPreferences != null) {
            _coins = _sharedPreferences.getInt("coins", -1);
            _coins += score;

            Editor editor = _sharedPreferences.edit();
            editor.putString("playerName", _playerName);
            editor.putInt("coins", _coins);
            editor.putInt("score", _score);
            editor.putInt("highScore", _highScore);
            editor.apply();
        }

        if (!_inventory.isEmpty()) {
            for (int i = _inventory.size() - 1; i >= 0; i--)
            {
                if (_inventory.get(i).quantity <= 0)
                    _inventory.remove(i);
            }
            SaveInventory(c);
        }
    }

    public void AddItem(Item Bought, Context c) {
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
        SaveInventory(c);
    }


    public String GetName() {return _playerName;}
    public void SetName(String value) {
        _playerName = value;
        Editor editor = _sharedPreferences.edit();
        editor.putString("playerName", _playerName);
        editor.apply();
    }
    public int GetScore() {return _score;}
    public void SetScore(int value) {
        _score = value;
        Editor editor = _sharedPreferences.edit();
        editor.putInt("score", _coins);
        editor.apply();
    }
    public int GetHighScore() {return _highScore;}
    public int GetCoins() {return  _coins;}
    public void SetCoins(int value) {
        _coins = value;
        Editor editor = _sharedPreferences.edit();
        editor.putInt("coins", _coins);
        editor.apply();
    }
    public boolean isFirstInit() {return _firstInit;}
    public Item GetHeldItem() {return _currentItem;}
    public void SetHeldItem(Item item) {_currentItem = item;}
    public Vector<Item> GetInventory() {return _inventory;}


    public void LoadInventory(String filename, Context c) {
        List<String> _ID = new ArrayList<>();
        List<String> _ItemName = new ArrayList<>();
        List<String> _Quantity = new ArrayList<>();

        File file = new File(c.getExternalFilesDir(null), filename);

        if (!file.exists()) return;
        try {
            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();

            while (line != null) {
                // Split the line into display name and value
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    _ID.add(parts[0]);
                    _ItemName.add(parts[1]);
                    _Quantity.add(parts[2]);
                }
                line = reader.readLine();
            }
            reader.close();
            Log.w("INVENTORY", "LoadInventory Success!");
        } catch (IOException e) {
            Log.e("INVENTORY", "LoadInventory Failed: ", e);
        }

        // Convert lists to arrays
        for (int i = 0; i < _ID.size(); i++)
        {
            int _tempID = Integer.parseInt(_ID.get(i));
            String _tempName = _ItemName.get(i);
            int _tempQuantity = Integer.parseInt(_Quantity.get(i));

            Item item = new Item(_tempID, _tempName, _tempQuantity);
            _inventory.add(item);
        }
    }
    private void SaveInventory(Context c) {
        File file = new File(c.getExternalFilesDir(null), "inventory.txt");
        try {
            // Create a new file at that path
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            // Write some data to the file
            for (int i = 0; i < _inventory.size(); i++)
            {
                Item current = _inventory.get(i);
                String data = current.ID + " " + current.name + " " + current.quantity;
                writer.append(data);
            }
            writer.close();
            Log.w("INVENTORY", "Saved Inventory!");
        } catch (IOException e) {
            Log.e("INVENTORY", "SaveInventory Failed: ", e);
        }
    }
}
