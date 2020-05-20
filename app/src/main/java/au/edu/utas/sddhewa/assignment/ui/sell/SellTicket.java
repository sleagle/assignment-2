package au.edu.utas.sddhewa.assignment.ui.sell;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.create.FormInteraction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellTicket extends Fragment implements FormInteraction {

    private Context context;
    private SQLiteDatabase db;

    public SellTicket(Context context, SQLiteDatabase db) {
        this.context = context;
        this.db = db;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View sellTickets = inflater.inflate(R.layout.fragment_sell_ticket, container, false);

        Spinner rafflesSpinner = sellTickets.findViewById(R.id.raffles_spinner);
        final Spinner numTicketsSpinner = sellTickets.findViewById(R.id.num_tickets_spinner);

        try {
            final List<Raffle> raffleList = RaffleTable.selectCurrentRaffles(db);

            final ArrayAdapter<Raffle> rafflesAdapter = new ArrayAdapter<>(
                    context, android.R.layout.simple_spinner_dropdown_item, raffleList);

            rafflesSpinner.setAdapter(rafflesAdapter);

            rafflesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("#### tets", raffleList.get(position).toString());

                    List<Integer> ticketsList = new ArrayList<>();

                    Raffle raffle = raffleList.get(position);
                    int ticketsRemaining = raffle.getNoOfTickets() - raffle.getTicketsSold();
                    int ticketsToSell = Math.min(ticketsRemaining, raffle.getMaxTickets());

                    for (int i = 1; i <= ticketsToSell; i++) {
                        ticketsList.add(i);
                    }

                    final ArrayAdapter<Integer> numTicketsAdapter = new ArrayAdapter<>(
                            context, android.R.layout.simple_spinner_dropdown_item, ticketsList);

                    numTicketsSpinner.setAdapter(numTicketsAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sellTickets;
    }

    @Override
    public void resetForm() {

    }

    @Override
    public void createEntity() {

    }
}
