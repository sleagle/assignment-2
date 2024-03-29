package au.edu.utas.sddhewa.assignment.ui.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.adapter.SoldTicketsAdapter;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTicketTable;
import au.edu.utas.sddhewa.assignment.dto.TicketsSoldDTO;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;
import au.edu.utas.sddhewa.assignment.util.Utility;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TicketDetails extends Fragment {

    private final FragmentManager fragmentManager;
    private final SQLiteDatabase db;
    private final Context context;
    private final Bundle bundle;

    public TicketDetails(FragmentManager fragmentManager, SQLiteDatabase db, Context context,
                         Bundle bundle) {
        this.fragmentManager = fragmentManager;
        this.db = db;
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View soldTickets =
                inflater.inflate(R.layout.fragment_ticket_details, container, false);

        Raffle raffle = bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE);
        TicketsSoldDTO ticketsSold = bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE_TICKET);

        TextView header = soldTickets.findViewById(R.id.lblTicketDetailsHead);
        header.setText(getResources().getString(R.string.ticket_detail_title, ticketsSold.getCustomer().getFullName()));
        return soldTickets;
    }
}
