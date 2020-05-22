package au.edu.utas.sddhewa.assignment.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class CustomWarningDialog extends DialogFragment {

    private FormInteraction createObj;
    private String type;
    private int difference;

    public CustomWarningDialog(FormInteraction obj, String type, int difference) {
        this.createObj = obj;
        this.type = type;
        this.difference = difference;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.warning_dialog_title);

        switch (type) {
            case Utility.KEY_MAX_BUY_ALERT:
                builder.setMessage(R.string.warning_dialog_max_buy_msg);
                break;
            case Utility.KEY_OVER_BUY_ALERT:
                builder.setMessage(getResources().getString(R.string.warning_dialog_over_buy_msg, difference));
                break;
        }


        builder.setNeutralButton(R.string.warning_dialog_dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        /*
         * code snippet: https://android--examples.blogspot.com/2016/10/android-alertdialog-button-text-and.html
         */
        AlertDialog dialog = builder.create();
        dialog.show();

        Button neutral = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        neutral.setTextColor(getResources().getColor(R.color.btn_dismiss));

        return dialog;
    }
}