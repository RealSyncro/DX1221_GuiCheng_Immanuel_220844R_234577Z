package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopUI {
    public int ID = -1;
    public LinearLayout LL_ItemContainer = null;
    public LinearLayout LL_Sale = null;
    public LinearLayout LL_BuyFunction = null;

    public TextView Text_ItemHeader = null;
    public ImageView Image_ItemIcon = null;
    public TextView Text_ItemCost = null;
    public Button Button_BuyItem = null;

    public ShopUI(int ID, LinearLayout Container, LinearLayout Sale, LinearLayout Buy,
                  TextView itemName, ImageView icon, TextView cost, Button buyButton)
    {
        this.ID = ID;
        LL_ItemContainer = Container;
        LL_Sale = Sale;
        LL_BuyFunction = Buy;

        Text_ItemHeader = itemName;
        Image_ItemIcon = icon;
        Text_ItemCost = cost;
        Button_BuyItem = buyButton;
    }
}
