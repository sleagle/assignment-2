package au.edu.utas.sddhewa.assignment.ui.alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.TicketTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.modal.Ticket;
import au.edu.utas.sddhewa.assignment.ui.view.RaffleDetail;
import au.edu.utas.sddhewa.assignment.util.AlertType;

public class CustomMarginDrawDialog extends DialogFragment {

    private RaffleDetail detail;
    private Raffle raffle;
    private SQLiteDatabase db;

    public CustomMarginDrawDialog(RaffleDetail detail, Raffle raffle, SQLiteDatabase db) {
        this.detail = detail;
        this.raffle = raffle;
        this.db = db;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.margin_draw_dialog);
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton(R.string.margin_draw_dialog_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                try {
                    String ticketNum = raffle.getRaffleNameForTicket() + input.getText();
                    Ticket winningTicket = TicketTable.getTicketByTicketNumber(db, ticketNum);
                    Log.d("### ", ""+String.valueOf(winningTicket == null)+"");
                    if (winningTicket != null) {
                        detail.getWinningCustomerDetails(winningTicket, ticketNum, db);
                    } else {
                        Log.d("### ", "in else part");
                        CustomErrorDialog customErrorDialog = new CustomErrorDialog(AlertType.NO_MARGIN_TICKET);
                        customErrorDialog.show(getActivity().getSupportFragmentManager(), "error");
                    }
                }catch (ParseException e) {
                    e.printStackTrace();
                }
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