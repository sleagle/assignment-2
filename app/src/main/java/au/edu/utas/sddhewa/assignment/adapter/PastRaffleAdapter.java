package au.edu.utas.sddhewa.assignment.adapter;

import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.Raffle;

public class PastRaffleAdapter extends ArrayAdapter<Raffle> {

    private  int layoutResourceId;

    private SQLiteDatabase db;

    public PastRaffleAdapter(@NonNull Context context, int resource, @NonNull List<Raffle> objects,
                             SQLiteDatabase db) {
        super(context, resource, objects);
        layoutResourceId = resource;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(layoutResourceId, parent, false);

        Raffle raffle = this.getItem(position);

        assert raffle != null;
        setPastRaffleDetails(raffle, row);

        return row;
    }

    private void setPastRaffleDetails(Raffle raffle, View row) {

        TextView lblRaffleP = row.findViewById(R.id.lblRaffleP);
        lblRaffleP.setText(raffle.getName());

        TextView lblPrize = row.findViewById(R.id.lblPrize);
        lblPrize.setText(raffle.getTicketPriceString());

        TextView lblDrawDateP = row.findViewById(R.id.lblDrawDateP);
        lblDrawDateP.setText(raffle.getDrawDate());

        TextView lblWinner = row.findViewById(R.id.lblWinner);
        try {
            Customer customer = CustomerTable.selectById(db, raffle.getWinningDetailsDTO().getTicketOwnerId());
            lblWinner.setText(customer.getFullName());
        } catch (ParseException e) {
            e.printStackTrace();;
        }
    }
}
