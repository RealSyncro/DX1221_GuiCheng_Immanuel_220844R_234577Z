package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

public class ShopUI {
    protected final Context context;
    public final Item item;

    public LinearLayout LL_ItemContainer; // Contains the entire item box.
    protected final LinearLayout LL_Sale; // Contains the content of the individual item.
    protected final LinearLayout LL_BuyFunction; // Contains the layout of buying item.

    protected final TextView Text_ItemHeader;
    protected final ImageView Image_ItemIcon;
    protected final TextView Text_ItemCost;
    public Button Button_BuyItem;

    public ShopUI(Item _item, Context _context, int imageDim)
    {
        context = _context;
        item = _item;

        // Item Container
        LL_ItemContainer = new LinearLayout(context);
        LL_ItemContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LL_ItemContainer.setOrientation(LinearLayout.VERTICAL);

        // Item Header
        Text_ItemHeader = new TextView(context);
        Text_ItemHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Text_ItemHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Text_ItemHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        Text_ItemHeader.setTextSize(34);
        Text_ItemHeader.setTextColor(Color.WHITE);
        Text_ItemHeader.append(item.name);
        LL_ItemContainer.addView(Text_ItemHeader);

        // Item Display
        LL_Sale = new LinearLayout(context);
        LL_Sale.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LL_Sale.setOrientation(LinearLayout.HORIZONTAL);

        // Item Icon
        Image_ItemIcon = new ImageView(context);
        Image_ItemIcon.setLayoutParams(new LinearLayout.LayoutParams(imageDim, imageDim));
        Image_ItemIcon.setImageResource(item.resImageID);
        LL_Sale.addView(Image_ItemIcon);

        // Item Buy Layout
        {
            LL_BuyFunction = new LinearLayout(context);
            LL_BuyFunction.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            LL_BuyFunction.setOrientation(LinearLayout.VERTICAL);

            Text_ItemCost = new TextView(context);
            Text_ItemCost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_ItemCost.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_ItemCost.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Text_ItemCost.setTextSize(34);
            Text_ItemCost.setTextColor(Color.WHITE);
            Text_ItemCost.append("Cost: " + item.cost);
            LL_BuyFunction.addView(Text_ItemCost);

            Button_BuyItem = new Button(context);
            ViewGroup.MarginLayoutParams _LP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            _LP.setMargins(40, 0, 40, 0);

            Button_BuyItem.setLayoutParams(_LP);
            Button_BuyItem.setBackgroundColor(Color.parseColor("#998227"));
            Button_BuyItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Button_BuyItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Button_BuyItem.setTextSize(34);
            Button_BuyItem.setTextColor(Color.WHITE);
            Button_BuyItem.setText(R.string.shop_sample_text);
            LL_BuyFunction.addView(Button_BuyItem);
        }
        LL_Sale.addView(LL_BuyFunction);
        LL_ItemContainer.addView(LL_Sale);
    }
}
