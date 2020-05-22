package au.edu.utas.sddhewa.assignment.adapter;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.dto.TicketsSoldDTO;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class SoldTicketsAdapter extends ArrayAdapter<TicketsSoldDTO> {

    private int layoutId;

    public SoldTicketsAdapter(@NonNull Context context, int resource, @NonNull List<TicketsSoldDTO> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(layoutId, parent, false);

        TicketsSoldDTO ticketsSoldDTO = this.getItem(position);

        assert ticketsSoldDTO != null;
        setRaffleTicketDetails(ticketsSoldDTO, row);

        return row;
    }

    private void setRaffleTicketDetails(TicketsSoldDTO ticketsSoldDTO, View row) {

        TextView lblCustomerName = row.findViewById(R.id.txtCustomerName);
        lblCustomerName.setText(ticketsSoldDTO.getCustomer().getFullName());

        TextView lblPurchasedOn = row.findViewById(R.id.txtPurchasedOn);
        String purchasedDate = "Purchased on: " +
                ticketsSoldDTO.getRaffleTicket().getPurchasedDate();
        lblPurchasedOn.setText(purchasedDate);

        TextView lblPurchasedTickets = row.findViewById(R.id.txtTotTickets);
        String numTickets = String.format("%d Tickets", ticketsSoldDTO.getRaffleTicket().getNumTickets());
        lblPurchasedTickets.setText(numTickets);

        TextView lblTotPrice = row.findViewById(R.id.txtTotPrice);
        String totPrice = "Total: " + Utility.getFormattedPrice(ticketsSoldDTO.getRaffleTicket().getTotalPrice());
        lblTotPrice.setText(totPrice);
    }
}
