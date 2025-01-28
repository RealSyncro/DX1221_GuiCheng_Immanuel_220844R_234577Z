package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.util.Log;

import androidx.annotation.DrawableRes;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

public class Item {
    public int ID;
    public @DrawableRes int resImageID;
    public @DrawableRes int InActiveID;
    public @DrawableRes int ActiveID;

    public String name;
    public int cost;
    public int quantity;

    public Item(int _ID, String _name, int _cost, int _quantity)
    {
        ID = _ID;
        name = _name;
        cost = _cost;
        quantity = _quantity;

        resImageID = -1;
        InActiveID = -1;
        ActiveID = -1;
        ItemResTable.Get().SetImageID(this);
    }

    public Item(Item _temp){
        ID = _temp.ID;
        name = _temp.name;
        cost = _temp.cost;
        quantity = _temp.quantity;

        resImageID = -1;
        InActiveID = -1;
        ActiveID = -1;
        ItemResTable.Get().SetImageID(this);
    }
}