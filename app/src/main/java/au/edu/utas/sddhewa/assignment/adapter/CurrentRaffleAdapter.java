package au.edu.utas.sddhewa.assignment.adapter;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.modal.Raffle;

public class CurrentRaffleAdapter extends ArrayAdapter<Raffle> {

    private  int layoutResourceId;

    private int tabId;

    public CurrentRaffleAdapter(@NonNull Context context, int resource, @NonNull List<Raffle> objects, int tabId) {

        super(context, resource, objects);
        layoutResourceId = resource;
        this.tabId = tabId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(layoutResourceId, parent, false);

        Raffle raffle = this.getItem(position);

        assert raffle != null;
        Log.d("***************TAB-ID", String.valueOf(tabId));

        /*if (tabId == 0) {
            setCurrentRaffleDetails(raffle, row);
        }
        else {
            setPastRaffleDetails(raffle, row);
        }*/

        setCurrentRaffleDetails(raffle, row);

        return row;
    }

    private void setCurrentRaffleDetails(Raffle raffle, View row) {

        TextView lblRaffle = row.findViewById(R.id.lblRaffle);
        lblRaffle.setText(raffle.getName());

        TextView lblDesc = row.findViewById(R.id.lblDesc);
        lblDesc.setText(raffle.getDescription());

        TextView lblRaffleType = row.findViewById(R.id.lblRaffleType);
        lblRaffleType.setText(raffle.getTypeId().name);

        TextView lblDrawDate = row.findViewById(R.id.lblDrawDate);
        lblDrawDate.setText(raffle.getDrawDate());
    }

    private void setPastRaffleDetails(Raffle raffle, View row) {

        TextView lblRaffleP = row.findViewById(R.id.lblRaffleP);
        lblRaffleP.setText(raffle.getName());

        TextView lblDescP = row.findViewById(R.id.lblDescP);
        lblDescP.setText(raffle.getDescription());

        TextView lblRaffleTypeP = row.findViewById(R.id.lblRaffleTypeP);
        lblRaffleTypeP.setText(raffle.getTypeId().name);

        TextView lblDrawDateP = row.findViewById(R.id.lblDrawDateP);
        lblDrawDateP.setText(raffle.getDrawDate());
    }
}
