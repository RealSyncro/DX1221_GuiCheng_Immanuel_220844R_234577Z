package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.R;
import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;

import java.util.Vector;

public class EquipDialog extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing() { return _isShowing; }

    private EquipUI _current;
    private final Vector<EquipUI> ref_itemList;
    private EquipUI _instance;

    public EquipDialog(Vector<EquipUI> refList, EquipUI instance) {
        ref_itemList = refList;
        _instance = instance;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _isShowing = true;
        return Show_Dialog();
    }

    // Dialog Behaviour
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        _isShowing = false;
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        _isShowing = false;
    }



    private void EquipItem(boolean swap)
    {
        // Swap current item with the new item, if there's a held item.
        if (swap) {
            _current.Button_Equip.setText(R.string.inventory_sample_text);
            _current.Button_Equip.setBackgroundColor(Color.parseColor("#1B7A16"));
        }

        CharSequence unEquipText = "Un-equip";
        _current = _instance;
        _current.Button_Equip.setText(unEquipText);
        _current.Button_Equip.setBackgroundColor(Color.parseColor("#7A1D16"));
        SaveSystem.Get().SetHeldItem(_current.item);
    }
    private void UnEquipItem()
    {
        _current.Button_Equip.setText(R.string.inventory_sample_text);
        _current.Button_Equip.setBackgroundColor(Color.parseColor("#1B7A16"));
        SaveSystem.Get().SetHeldItem(null);
        _instance = null;
        _current = null;
    }
    private AlertDialog Show_Dialog()
    {
        // If there is already an equipped item.
        _current = GetEquippedItemUI();
        if (_current != null)
        {
            // Case 1: Equipped item same as this item instance. (De-Equip)
            // Case 2: Equipped Item different from item instance. (Swap)
            if (_current.item.ID == _instance.item.ID)
                return Build_UnEquipDialog();
            else
                return Build_EquipDialog(true);
        }

        // Case 3: There is no current equipped item.
        return Build_EquipDialog(false);
    }
    private AlertDialog Build_EquipDialog(boolean swap)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Equip Item");
        builder.setMessage("Would you like to equip " + _instance.item.name + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> EquipItem(swap));
        builder.setNegativeButton("No", null);
        return builder.create();
    }
    private AlertDialog Build_UnEquipDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Un-equip Item");
        builder.setMessage("Would you like to un-equip " + _current.item.name + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> UnEquipItem());
        builder.setNegativeButton("No", null);
        return builder.create();
    }
    private EquipUI GetEquippedItemUI()
    {
        Item heldItem = SaveSystem.Get().GetHeldItem();
        if (heldItem != null)
        {
            for (EquipUI UI : ref_itemList)
                if (UI.item.ID == heldItem.ID) return UI;
        }
        return null;
    }
}
