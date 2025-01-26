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

public class EquipUI {
    public final Item item;

    public LinearLayout LL_Layout; // Contains the entire item box.
    protected final LinearLayout LL_Display; // Contains the content of the individual item.
    protected final TextView Text_Name;
    protected final ImageView Image_Icon;
    protected final TextView Text_Quantity;
    public Button Button_Equip;

    public EquipUI(Item _item, Context c, int imageDim)
    {
        item = _item;

        // Item Container
        LL_Layout = new LinearLayout(c);
        LL_Layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LL_Layout.setOrientation(LinearLayout.HORIZONTAL);
        LL_Layout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        // Item Icon
        Image_Icon = new ImageView(c);
        Image_Icon.setLayoutParams(new LinearLayout.LayoutParams(imageDim, imageDim));
        Image_Icon.setImageResource(item.resImageID);
        LL_Layout.addView(Image_Icon);


        // Item Equip Layout
        {
            LL_Display = new LinearLayout(c);
            LL_Display.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            LL_Display.setOrientation(LinearLayout.VERTICAL);
            LL_Display.setVerticalGravity(Gravity.CENTER_HORIZONTAL);

            // Item Name
            Text_Name = new TextView(c);
            Text_Name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_Name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_Name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Text_Name.setTextSize(28);
            Text_Name.setTextColor(Color.WHITE);
            Text_Name.append(item.name);
            LL_Display.addView(Text_Name);

            // Item Quantity
            Text_Quantity = new TextView(c);
            Text_Quantity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Text_Quantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Text_Quantity.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            Text_Quantity.setTextSize(26);
            Text_Quantity.setTextColor(Color.WHITE);
            Text_Quantity.append("Quantity: " + item.quantity);
            LL_Display.addView(Text_Quantity);

            // Equip Item
            Button_Equip = new Button(c);
            ViewGroup.MarginLayoutParams _LP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            _LP.setMargins(12, 0, 12, 0);

            Button_Equip.setLayoutParams(_LP);
            Button_Equip.setBackgroundColor(Color.parseColor("#1B7A16"));
            Button_Equip.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Button_Equip.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            Button_Equip.setTextSize(30);
            Button_Equip.setTextColor(Color.WHITE);
            Button_Equip.setText(R.string.inventory_sample_text);
            LL_Display.addView(Button_Equip);
        }
        LL_Layout.addView(LL_Display);
    }
}
