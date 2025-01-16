package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.pages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.AudioManager;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui.ShopUI;

import java.util.Vector;

public class ShopPage extends Activity implements View.OnClickListener {
    private Button _backButton;
    private LinearLayout _shopLayout;
    private final Vector<ShopUI> _shopItemUI = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_page);

        _backButton = findViewById(R.id.shop_back_button);
        _backButton.setOnClickListener(this);

        // Main Scroll UI
        _shopLayout = findViewById(R.id.shop_layout);
        UploadShopItemUI(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioManager.Get().PlaySFX(this, R.raw.button_click);
    }

    @Override
    public void onClick(View v) {
        if (v == _backButton) {
            startActivity(new Intent().setClass(this, MainMenu.class));
        }

        if (!_shopItemUI.isEmpty()) {
            for (ShopUI item : _shopItemUI) {
                if (v == item.Button_BuyItem) {
                    // Do Something
                    break;
                }
            }
        }
    }


    private void UploadShopItemUI(int id) {
        LinearLayout LL_ItemContainer; // Contains the entire item box.
        LinearLayout LL_Sale; // Contains the content of the individual item.
        LinearLayout LL_BuyFunction; // Contains the layout of buying item.

        TextView Text_ItemHeader;
        ImageView Image_ItemIcon;
        TextView Text_ItemCost;
        Button Button_BuyItem;


        // Item Container
        LL_ItemContainer = new LinearLayout(this);
        LL_ItemContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LL_ItemContainer.setOrientation(LinearLayout.VERTICAL);

        // Item Header
        Text_ItemHeader = new TextView(this);
        Text_ItemHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Text_ItemHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Text_ItemHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        Text_ItemHeader.setTextSize(34);
        Text_ItemHeader.setTextColor(Color.WHITE);
        Text_ItemHeader.append("Item Header 01");
        LL_ItemContainer.addView(Text_ItemHeader);

        // Item Display
        LL_Sale = new LinearLayout(this);
        LL_Sale.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LL_Sale.setOrientation(LinearLayout.HORIZONTAL);

        // Item Icon
        Image_ItemIcon = new ImageView(this);
        Image_ItemIcon.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(150), dpToPx(150)));
        Image_ItemIcon.setImageResource(R.drawable.collider);
        LL_Sale.addView(Image_ItemIcon);

        // Item Buy Layout
        {
            LL_BuyFunction = new LinearLayout(this);
            LL_BuyFunction.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            LL_BuyFunction.setOrientation(LinearLayout.VERTICAL);

            Text_ItemCost = new TextView(this);
            Text_ItemCost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_ItemCost.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_ItemCost.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Text_ItemCost.setTextSize(34);
            Text_ItemCost.setTextColor(Color.WHITE);
            Text_ItemCost.append("Cost: 300");
            LL_BuyFunction.addView(Text_ItemCost);

            Button_BuyItem = new Button(this);
            Button_BuyItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Button_BuyItem.setBackgroundColor(Color.parseColor("#216EC7"));
            Button_BuyItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Button_BuyItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Button_BuyItem.setTextSize(34);
            Button_BuyItem.setTextColor(Color.WHITE);
            Button_BuyItem.setText(R.string.shop_sample_text);
            Button_BuyItem.setOnClickListener(this);
            LL_BuyFunction.addView(Button_BuyItem);
        }
        LL_Sale.addView(LL_BuyFunction);
        LL_ItemContainer.addView(LL_Sale);

        ShopUI item = new ShopUI(id, LL_ItemContainer, LL_Sale, LL_BuyFunction,
                    Text_ItemHeader, Image_ItemIcon, Text_ItemCost, Button_BuyItem);

        _shopItemUI.add(item);
        _shopLayout.addView(LL_ItemContainer);
    }

    public int dpToPx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
