package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra.Item;

public class ShopUI {
    protected final Context context;
    public final Item item;

    public LinearLayout LL_ItemLayout; // Contains the entire item box.
    protected final LinearLayout LL_Sale; // Contains the content of the individual item.

    protected final TextView Text_ItemHeader;
    protected final ImageView Image_ItemIcon;
    protected final TextView Text_ItemCost;
    public Button Button_BuyItem;

    public ShopUI(Item _item, Context _context, int imageDim)
    {
        context = _context;
        item = _item;

        // Item Container
        ViewGroup.MarginLayoutParams _LP1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        _LP1.setMargins(0, 0, 0, 48);

        LL_ItemLayout = new LinearLayout(context);
        LL_ItemLayout.setLayoutParams(_LP1);
        LL_ItemLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Item Icon
        Image_ItemIcon = new ImageView(context);
        Image_ItemIcon.setLayoutParams(new LinearLayout.LayoutParams(imageDim, imageDim));
        Image_ItemIcon.setImageResource(item.resImageID);
        LL_ItemLayout.addView(Image_ItemIcon);

        // Item Display
        {
            LL_Sale = new LinearLayout(context);
            LL_Sale.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            LL_Sale.setOrientation(LinearLayout.VERTICAL);
            LL_Sale.setGravity(Gravity.CENTER_HORIZONTAL);

            // Item Header
            Text_ItemHeader = new TextView(context);
            Text_ItemHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_ItemHeader.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_ItemHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Text_ItemHeader.setTextSize(34);
            Text_ItemHeader.setTextColor(Color.WHITE);
            Text_ItemHeader.append(item.name);
            LL_Sale.addView(Text_ItemHeader);

            // Item Cost
            Text_ItemCost = new TextView(context);
            Text_ItemCost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_ItemCost.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_ItemCost.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            Text_ItemCost.setTextSize(28);
            Text_ItemCost.setTextColor(Color.WHITE);
            Text_ItemCost.append("Cost: " + item.cost);
            LL_Sale.addView(Text_ItemCost);

            Button_BuyItem = new Button(context);
            ViewGroup.MarginLayoutParams _LP2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            _LP2.setMargins(20, 0, 20, 0);

            Button_BuyItem.setLayoutParams(_LP2);
            Button_BuyItem.setBackgroundColor(Color.parseColor("#998227"));
            Button_BuyItem.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Button_BuyItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Button_BuyItem.setTextSize(34);
            Button_BuyItem.setTextColor(Color.WHITE);
            Button_BuyItem.setText(R.string.shop_sample_text);
            LL_Sale.addView(Button_BuyItem);
        }
        LL_ItemLayout.addView(LL_Sale);
    }
}
