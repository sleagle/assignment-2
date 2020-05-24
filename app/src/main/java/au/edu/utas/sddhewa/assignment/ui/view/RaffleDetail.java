package au.edu.utas.sddhewa.assignment.ui.view;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomWarningDialog;
import au.edu.utas.sddhewa.assignment.ui.home.Home;
import au.edu.utas.sddhewa.assignment.util.AlertType;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class RaffleDetail extends Fragment {

    private final Bundle bundle;
    private final SQLiteDatabase db;

    public RaffleDetail(Bundle bundle, SQLiteDatabase db) {
        this.bundle = bundle;
        this.db = db;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View viewRaffle = inflater.inflate(R.layout.fragment_view_raffle, container, false);

        final Raffle raffle = (Raffle) bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE);
        Log.d("#### rafflelist onclick", raffle.toString());

        TextView name = viewRaffle.findViewById(R.id.txtName_RD);
        name.setText(raffle.getName());

        TextView prize = viewRaffle.findViewById(R.id.txtPrize);
        prize.setText(raffle.getRafflePrizeString());

        TextView description = viewRaffle.findViewById(R.id.txtDesc_RD);
        description.setText(raffle.getDescription());

        TextView raffleType = viewRaffle.findViewById(R.id.txtRaffleType_RD);
        raffleType.setText(raffle.getTypeId().name);

        TextView startDate = viewRaffle.findViewById(R.id.txtStartDate_RD);
        startDate.setText(raffle.getStartingDateString());

        TextView drawDate = viewRaffle.findViewById(R.id.txtDrawDate_RD);
        drawDate.setText(raffle.getDrawDate());

        TextView location = viewRaffle.findViewById(R.id.txtLocation_RD);
        location.setText(raffle.getLocation());

        TextView ticketPrice = viewRaffle.findViewById(R.id.txtTicketPrice_RD);
        ticketPrice.setText(raffle.getTicketPriceString());

        TextView numTickets = viewRaffle.findViewById(R.id.txtNumTickets_RD);
        numTickets.setText(String.valueOf(raffle.getNoOfTickets()));

        TextView maxTickets = viewRaffle.findViewById(R.id.txtMaxTickets_RD);
        maxTickets.setText(String.valueOf(raffle.getMaxTickets()));

        ImageView imageView = viewRaffle.findViewById(R.id.imgCover_RD);
        byte[] coverImage = raffle.getRaffleCover();
        if (coverImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length);
            imageView.setImageBitmap(bitmap);
        }

        Button drawButton = viewRaffle.findViewById(R.id.btnDraw);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button editButton = viewRaffle.findViewById(R.id.btnEdit_RD);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button deleteButton = viewRaffle.findViewById(R.id.btnDelete_RD);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (raffle.getTicketsSold() > 0) {
                    CustomWarningDialog customWarningDialog = new CustomWarningDialog(AlertType.NOT_DELETED_SINGLE);
                    customWarningDialog.setNotDeletedRaffleName(raffle.getName());
                    customWarningDialog.show(getActivity().getSupportFragmentManager(), "warning");
                }
                else {
                    RaffleTable.deleteRaffle(db, raffle);

                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,
                                    new Home(db, getActivity().getSupportFragmentManager(),
                                            getContext()))
                            .commit();
                }
            }
        });

        return viewRaffle;
    }
}
