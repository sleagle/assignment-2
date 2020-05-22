package au.edu.utas.sddhewa.assignment.ui.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class TicketsSold extends Fragment {

    private final Context context;
    private final SQLiteDatabase db;
    private final Bundle bundle;
    private Raffle raffle;

    public TicketsSold(Context context, SQLiteDatabase db, Bundle bundle) {
        this.context = context;
        this.db = db;
        this.bundle = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ticketsSoldTab = inflater.inflate(R.layout.fragment_tickets_sold, container, false);

        raffle = bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE);

        ListView ticketsList = ticketsSoldTab.findViewById(R.id.ticketsList);

        try {
            final List<RaffleTicket> raffleTickets = RaffleTicketTable.selectAllByRaffleId(db, raffle.getRaffleId());

            final List<TicketsSoldDTO> ticketsSold = new ArrayList<>();
            for (RaffleTicket rt: raffleTickets) {
                Customer customer = CustomerTable.selectById(db, rt.getCustomerId());
                ticketsSold.add(new TicketsSoldDTO(rt, customer));
            }

            final SoldTicketsAdapter soldTicketsAdapter = new SoldTicketsAdapter(
                    context, R.layout.list_sold_tickets, ticketsSold);

            ticketsList.setAdapter(soldTicketsAdapter);

            ticketsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    bundle.putParcelable(Utility.KEY_SELECTED_RAFFLE_TICKET,
                            ticketsSold.get(position));

                    bundle.putParcelable(Utility.KEY_SELECTED_RAFFLE, raffle);

                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,
                                    new TicketDetails(getActivity().getSupportFragmentManager(), db,
                                            context, bundle))
                            .addToBackStack(null).commit();
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ticketsSoldTab;
    }
}
