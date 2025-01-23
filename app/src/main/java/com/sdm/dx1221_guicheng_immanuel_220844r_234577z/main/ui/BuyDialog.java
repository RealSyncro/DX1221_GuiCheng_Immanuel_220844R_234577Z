package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.main.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core.GameActivity;

public class BuyDialog extends DialogFragment {

    private static boolean _isShowing = false;
    public static boolean isShowing() { return _isShowing; }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _isShowing = true;

        // Build Dialog Box
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Return to main menu?");
        builder.setPositiveButton("Yes", (dialog, which) -> GameActivity.instance.ExitGame());
        builder.setNegativeButton("No", null);
        return builder.create();
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
}