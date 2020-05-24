package au.edu.utas.sddhewa.assignment.ui.alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.util.AlertType;

public class CustomWarningDialog extends DialogFragment {

    private AlertType type;
    private int difference;
    private List<String> notDeletedList;
    private String notDeletedRaffleName;

    public CustomWarningDialog(AlertType type, int difference) {
        this.type = type;
        this.difference = difference;
    }

    public CustomWarningDialog(AlertType type) {
        this.type = type;
    }

    public void setNotDeletedList(List<String> list) {
        this.notDeletedList = list;
    }

    public void setNotDeletedRaffleName(String raffleName) {
        this.notDeletedRaffleName = raffleName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.warning_dialog_title);
        builder.setIcon(R.drawable.ic_warning);

        switch (type) {
            case MAX_BUY_ALERT:
                builder.setMessage(R.string.warning_dialog_max_buy_msg);
                break;
            case OVER_BUY_ALERT:
                builder.setMessage(getResources().getString(R.string.warning_dialog_over_buy_msg, difference));
                break;
            case NOT_DELETED_MULTI:
                builder.setMessage(getResources().getString(
                        R.string.error_not_deleted_msg_multi,
                        Arrays.toString(notDeletedList.toArray())));
                break;
            case NOT_DELETED_SINGLE:
                builder.setMessage(getResources().getString(
                        R.string.error_not_deleted_msg_single, notDeletedRaffleName));
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