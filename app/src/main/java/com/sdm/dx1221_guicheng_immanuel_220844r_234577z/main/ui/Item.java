package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;

public class Item {
    public int ID;
    public int resImageID;
    public String name;
    public int cost;
    public int quantity;

    public Item(int _ID, String _name, int _cost)
    {
        ID = _ID;
        resImageID = RetrieveImageID();
        name = _name;
        cost = _cost;
        quantity = 1;
    }

    public Item(Item _temp){
        ID = _temp.ID;
        resImageID = _temp.resImageID;
        name = _temp.name;
        cost = _temp.cost;
        quantity = 1;
    }

    public int RetrieveImageID()
    {
        int foundID = -1;
        int[] ImageID = new int[2];

        ImageID[0] = R.drawable.collider;
        ImageID[1] = R.drawable.active_jump;

        for (int i = 0; i < ImageID.length; i++)
            if (ID == i) foundID = ImageID[i];

        return foundID;
    }
}
