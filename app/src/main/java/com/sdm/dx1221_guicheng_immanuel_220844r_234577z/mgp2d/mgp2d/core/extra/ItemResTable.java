package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.extra;

import android.util.Log;

import androidx.annotation.DrawableRes;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

public class ItemResTable {
    private static ItemResTable instance = null;
    private final int U_ITEM = 2;
    @DrawableRes
    public final int[] ImageID = new int[U_ITEM];
    @DrawableRes
    public final int[] InActiveID = new int[U_ITEM];
    @DrawableRes
    public final int[] ActiveID = new int[U_ITEM];

    public ItemResTable()
    {
        ImageID[0] = R.drawable.score;
        ImageID[1] = R.drawable.shield_potion;

        InActiveID[0] = R.drawable.c_inactive_button_score;
        InActiveID[1] = R.drawable.c_inactive_button_shieldpot;

        ActiveID[0] = R.drawable.c_active_button_score;
        ActiveID[1] = R.drawable.c_active_button_shieldpot;
    }

    public static ItemResTable Get()
    {
        if (instance == null) instance = new ItemResTable();
        return instance;
    }

    public void SetImageID(Item item)
    {
        if (item.ID < 0 || item.ID > (U_ITEM - 1) ) {
            Log.e("ITEM_RESOURCE", "SetImage: Out of Bounds!");
            return;
        }

        item.resImageID = ImageID[item.ID];
        item.InActiveID = InActiveID[item.ID];
        item.ActiveID = ActiveID[item.ID];
    }
}
