package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioController;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.FileSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.BuyDialog;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.Item;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.ShopUI;

import java.util.Vector;

public class ShopPage extends FragmentActivity implements View.OnClickListener {
    private Button _backButton;
    private LinearLayout _shopLayout;
    private TextView Text_UserCoins;
    private final Vector<Item> _items = new Vector<>();
    private final Vector<ShopUI> _shopItemUI = new Vector<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_page);

        _backButton = findViewById(R.id.shop_back_button);
        _backButton.setOnClickListener(this);

        // Scrollable Shop Container
        _shopLayout = findViewById(R.id.shop_layout);

        Text_UserCoins = findViewById(R.id.shop_currency_text);

        LoadShop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioController.Get().StopAllSFXPlayer();
        AudioController.Get().PlaySFX(R.raw.button_click);

        // Update coins
        CharSequence coins = "Coins: " + SaveSystem.Get().GetCoins();
        Text_UserCoins.append(coins);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, OtherPage.class));
        }

        if (!_shopItemUI.isEmpty()) {
            for (ShopUI SUI : _shopItemUI) {
                if (v == SUI.Button_BuyItem) {
                    // When Buy item button is pressed.
                    if (!BuyDialog.isShowing()) {
                        BuyDialog confirmBuy = new BuyDialog(_shopItemUI, SUI.item, Text_UserCoins);
                        confirmBuy.show(getSupportFragmentManager(), "Buy Dialog");
                    }
                    break;
                }
            }
        }
    }

    private void LoadShop() {
        FileSystem.LoadShop("Items.txt", _items, this);
        for (int i = 0; i < _items.size(); i++)
            UploadShopItemUI(_items.get(i));
    }
    private void UploadShopItemUI(Item item) {
        ShopUI _shopItem = new ShopUI(item, this, dpToPx(150));
        _shopItem.Button_BuyItem.setOnClickListener(this);

        _shopItemUI.add(_shopItem);
        _shopLayout.addView(_shopItem.LL_ItemLayout);
    }
    private int dpToPx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
