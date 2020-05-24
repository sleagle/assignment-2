package au.edu.utas.sddhewa.assignment.ui.alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.ui.view.RaffleDetail;

public class CustomWinnerDialog extends DialogFragment {

    private RaffleDetail detail;
    private String ticketNumber;
    private String winner;

    public CustomWinnerDialog(RaffleDetail detail, String ticketNumber, String winner) {
        this.detail = detail;
        this.ticketNumber = ticketNumber;
        this.winner = winner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.winner_dialog_title);
        builder.setIcon(R.drawable.ic_info);
        builder.setMessage(getResources().getString(R.string.winner_dialog_msg, ticketNumber, winner));

        builder.setNeutralButton(R.string.warning_dialog_dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                detail.goBackToHomePage();
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