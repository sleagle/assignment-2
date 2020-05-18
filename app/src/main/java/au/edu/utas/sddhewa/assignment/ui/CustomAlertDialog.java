package au.edu.utas.sddhewa.assignment.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.ui.create.Create;

public class CustomAlertDialog extends DialogFragment {

    private Create createObj;

    public CustomAlertDialog(Create obj) {
        this.createObj = obj;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.alert_dialog_title);
        builder.setMessage(R.string.alert_dialog_msg);

        builder.setPositiveButton(R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                createObj.resetForm();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(getResources().getColor(R.color.btn_success));

        Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negative.setTextColor(getResources().getColor(R.color.btn_discard));

        return dialog;
    }
}