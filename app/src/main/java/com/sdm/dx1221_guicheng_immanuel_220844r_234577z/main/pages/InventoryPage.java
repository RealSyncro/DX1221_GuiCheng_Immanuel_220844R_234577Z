package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.EquipDialog;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.EquipUI;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;

import java.util.Vector;

public class InventoryPage extends FragmentActivity implements View.OnClickListener {
    private Button _backButton;
    private LinearLayout _inventoryLayout;
    private TextView Text_UserCoins;
    private final Vector<EquipUI> _inventoryListUI = new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_page);

        _backButton = findViewById(R.id.inventory_back_button);
        _backButton.setOnClickListener(this);

        // Scrollable Shop Container
        _inventoryLayout = findViewById(R.id.inventory_layout);
        Text_UserCoins = findViewById(R.id.inventory_currency_text);

        LoadInventoryUI();
        Log.w("INVENTORY_PAGE", "Inventory EquipUI: " + _inventoryListUI.size());
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().StopAllSFXPlayer();
        AudioController.Get().PlayBGM(this, R.raw.generic_music);
        AudioController.Get().PlaySFX(R.raw.button_click);

        // Update coins
        CharSequence coins = "Coins: " + SaveSystem.Get().GetCoins();
        Text_UserCoins.append(coins);
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
            startActivity(new Intent().setClass(this, OtherPage.class));
        }

        if (!_inventoryListUI.isEmpty()) {
            for (EquipUI EUI : _inventoryListUI) {
                if (v == EUI.Button_Equip) {
                    // When Equip item button is pressed.
                    if (!EquipDialog.isShowing()) {
                        EquipDialog equip = new EquipDialog(_inventoryListUI, EUI);
                        equip.show(getSupportFragmentManager(), "Equip Dialog");
                    }
                    break;
                }
            }
        }
    }

    private void LoadInventoryUI() {
        Vector<Item> inventory = SaveSystem.Get().GetInventory();

        if (inventory.isEmpty()) {
            // Show no items text
            TextView Text_NoItem = new TextView(this);
            Text_NoItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_NoItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_NoItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Text_NoItem.setTextSize(32);
            Text_NoItem.setTextColor(Color.WHITE);

            CharSequence noItem = "No Items Available to equip.";
            Text_NoItem.append(noItem);
            _inventoryLayout.addView(Text_NoItem);
            return;
        }

        for (int i = 0; i < inventory.size(); i++)
            UploadItemUI(inventory.get(i));
    }
    private void UploadItemUI(Item item) {
        EquipUI inventoryItem = new EquipUI(item, this, dpToPx(150));
        inventoryItem.Button_Equip.setOnClickListener(this);
        _inventoryListUI.add(inventoryItem);
        _inventoryLayout.addView(inventoryItem.LL_Layout);

        Item held = SaveSystem.Get().GetHeldItem();
        if (held != null)
        {
            if (held.ID == item.ID)
            {
                CharSequence unEquipText = "Un-equip";
                inventoryItem.Button_Equip.setText(unEquipText);
                inventoryItem.Button_Equip.setBackgroundColor(Color.parseColor("#7A1D16"));
            }
        }
    }
    private int dpToPx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
