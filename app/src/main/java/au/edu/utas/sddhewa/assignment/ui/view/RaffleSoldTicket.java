package au.edu.utas.sddhewa.assignment.ui.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.adapter.SoldTicketsAdapter;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTicketTable;
import au.edu.utas.sddhewa.assignment.dto.TicketsSoldDTO;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RaffleSoldTicket extends Fragment {

    private final long raffleId;
    private final SQLiteDatabase db;
    private final Context context;

    public RaffleSoldTicket(long raffleId, SQLiteDatabase db, Context context) {
        this.raffleId = raffleId;
        this.db = db;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View soldTickets =
                inflater.inflate(R.layout.fragment_raffle_sold_ticket, container, false);

        ListView ticketsList = soldTickets.findViewById(R.id.ticketsList);

        try {
            List<RaffleTicket> raffleTickets = RaffleTicketTable.selectAllByRaffleId(db, raffleId);

            List<TicketsSoldDTO> ticketsSold = new ArrayList<>();
            for (RaffleTicket rt: raffleTickets) {
                Customer customer = CustomerTable.selectById(db, rt.getCustomerId());

                ticketsSold.add(new TicketsSoldDTO(rt, customer));
            }

            final SoldTicketsAdapter soldTicketsAdapter = new SoldTicketsAdapter(
                    context, R.layout.list_sold_tickets, ticketsSold);

            ticketsList.setAdapter(soldTicketsAdapter);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return soldTickets;
    }
}
