package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.common.SaveSystem;

import java.util.Vector;


public class BuyDialog extends DialogFragment {

    private static boolean _isShowing = false;
    public static boolean isShowing() { return _isShowing; }

    private final Vector<ShopUI> ref_ShopList;
    private final Item _toBeBought;
    private final TextView ref_CoinUI;


    public BuyDialog(Vector<ShopUI> refList, Item item, TextView userCoinUI) {
        _toBeBought = item;
        ref_ShopList = refList;
        ref_CoinUI = userCoinUI;
    }

    public void PurchaseItem() {
        int currentCoins = SaveSystem.Get().GetCoins();
        currentCoins -= _toBeBought.cost;

        SaveSystem.Get().AddItem(_toBeBought, getContext());
        SaveSystem.Get().SetCoins(currentCoins);
        CharSequence coinText = "Coins: " + (currentCoins);
        ref_CoinUI.setText(coinText);

        AlertDialog receipt = Build_PurchasedDialog();
        receipt.show();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _isShowing = true;

        // Build dialog box based on conditions.
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


    private AlertDialog Show_Dialog()
    {
        int currentCoins = SaveSystem.Get().GetCoins();

        if (currentCoins >= _toBeBought.cost)
            return Build_BuyDialog();

        return Build_InsufficientDialog();
    }
    private AlertDialog Build_BuyDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Purchase Confirmation");
        builder.setMessage("Confirm Purchase for " + _toBeBought.name + "?\n(" + _toBeBought.cost + " Coins)");
        builder.setPositiveButton("Yes", (dialog, which) -> PurchaseItem());
        builder.setNegativeButton("No", null);
        return builder.create();
    }
    private AlertDialog Build_PurchasedDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Purchase Success");
        builder.setMessage("You have successfully purchased " + _toBeBought.name + "!");
        builder.setPositiveButton("OK", null);
        return builder.create();
    }
    private AlertDialog Build_InsufficientDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Purchase Failed");
        builder.setMessage("You do not have enough coins for " + _toBeBought.name + ".");
        builder.setPositiveButton("OK", null);
        return builder.create();
    }
    private ShopUI GetCurrentShopUI()
    {
        if (ref_ShopList != null)
        {
            for (ShopUI ui : ref_ShopList)
                if (ui.item.ID == _toBeBought.ID) return ui;
        }
        return null;
    }
}
