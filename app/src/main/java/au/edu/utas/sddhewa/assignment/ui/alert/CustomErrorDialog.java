package au.edu.utas.sddhewa.assignment.ui.alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.util.AlertType;

public class CustomErrorDialog extends DialogFragment {

    private AlertType errorType;

    public CustomErrorDialog(AlertType type) {
        this.errorType = type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.drawable.ic_error);

        switch (errorType) {
            case CREATE_ERROR:
                builder.setTitle(R.string.error_dialog_create_title);
                builder.setMessage(R.string.error_dialog_create_msg);
                break;
            case CREATE_ERROR_RAFFLE:
                builder.setTitle(R.string.error_dialog_create_title);
                builder.setMessage(R.string.error_dialog_create_raffle_msg);
                break;
            case MULTI_SELECT_ERROR:
                builder.setTitle(R.string.error_dialog_select_title);
                builder.setMessage(R.string.error_dialog_edit_multi_msg);
                break;
            case NON_SELECT:
                builder.setTitle(R.string.error_dialog_select_title);
                builder.setMessage(R.string.error_dialog_no_select_msg);
                break;
            case NO_MARGIN_TICKET:
                builder.setTitle(R.string.error_margin_draw_title);
                builder.setMessage(R.string.error_margin_draw_msg);
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
