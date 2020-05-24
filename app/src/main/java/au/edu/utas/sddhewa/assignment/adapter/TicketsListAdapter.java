package au.edu.utas.sddhewa.assignment.adapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.dto.TicketsSoldDTO;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.modal.Ticket;

public class TicketsListAdapter extends ArrayAdapter<Ticket> {

    private int layoutId;
    private Raffle raffle;
    private TicketsSoldDTO ticketsSoldDTO;

    public TicketsListAdapter(@NonNull Context context, int resource, @NonNull List<Ticket> objects,
                              Raffle raffle, TicketsSoldDTO ticketsSoldDTO) {
        super(context, resource, objects);
        layoutId = resource;
        this.raffle = raffle;
        this.ticketsSoldDTO = ticketsSoldDTO;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(layoutId, parent, false);

        Ticket ticket = this.getItem(position);

        assert ticket != null;
        setTicketDetails(ticket, row);

        return row;
    }

    private void setTicketDetails(final Ticket ticket, View row) {

        TextView raffleName = row.findViewById(R.id.txtRaffleName);
        raffleName.setText(raffle.getName());

        TextView purchasedDate = row.findViewById(R.id.txtPurchasedDate);
        purchasedDate.setText("Purchased Date: " + ticketsSoldDTO.getRaffleTicket().getPurchasedDate());

        TextView txtTicketNumber = row.findViewById(R.id.txtTicketNumber);
        txtTicketNumber.setText("Ticket No: " + ticket.getTicketNumber());

        TextView txtPrice = row.findViewById(R.id.txtPrice);
        txtPrice.setText("Price: " + raffle.getTicketPriceString());

        Button shareButton = row.findViewById(R.id.btnShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = new StringBuilder(ticketsSoldDTO.getCustomer()
                        .getFullName()).append(" Purchased a ticket for the Raffle: ")
                        .append(raffle.getName()).append("\n").append("On: ")
                        .append(ticketsSoldDTO.getRaffleTicket().getPurchasedDate())
                        .append(" Ticket Number: ").append(ticket.getTicketNumber())
                        .append(" price of the ticket is: ")
                        .append(raffle.getTicketPriceString()).toString();

                /* Week 7 lectures */
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/plain");

                getContext().startActivity(Intent.createChooser(sendIntent, "Share via..."));
            }
        });
    }

}
