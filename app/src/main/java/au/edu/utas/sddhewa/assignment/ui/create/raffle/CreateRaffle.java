package au.edu.utas.sddhewa.assignment.ui.create.raffle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.CustomAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.create.Create;
import au.edu.utas.sddhewa.assignment.util.RaffleType;
import au.edu.utas.sddhewa.assignment.util.Utility;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateRaffle extends Fragment implements Create {

    private Context context;
    private SQLiteDatabase db;
    private FragmentManager fm;

    private TextView name;
    private TextView description;
    private Spinner raffleType;
    private TextView startDate;
    private TextView drawDate;
    private TextView location;
    private TextView ticketPrice;
    private Spinner numTickets;
    private TextView maxTickets;
    private CheckBox addCoverImage;


    public CreateRaffle(Context context, SQLiteDatabase db, FragmentManager fm) {
        // Required empty public constructor
        this.context = context;
        this.db = db;
        this.fm = fm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View createRaffle = inflater.inflate(R.layout.fragment_create_raffle, container, false);

        final CreateRaffle createRaffleObj = this;

        name = createRaffle.findViewById(R.id.txtName);
        description = createRaffle.findViewById(R.id.txtDesc);
        raffleType = createRaffle.findViewById(R.id.raffle_type_spinner);
        startDate = createRaffle.findViewById(R.id.txtStartDate);
        drawDate = createRaffle.findViewById(R.id.txtCR_DrawDate);
        location = createRaffle.findViewById(R.id.txtLocation);
        ticketPrice = createRaffle.findViewById(R.id.txTicketPrice);
        numTickets = createRaffle.findViewById(R.id.no_tickets_spinner);
        maxTickets = createRaffle.findViewById(R.id.txtMaxTickets);
        addCoverImage = createRaffle.findViewById(R.id.cbxCoverImage);

        final TextView lblCoverImage = createRaffle.findViewById(R.id.lblCoverImage);
        lblCoverImage.setVisibility(View.INVISIBLE);

        Button startDateSelect = createRaffle.findViewById(R.id.btnCalander);
        Button drawDateSelect = createRaffle.findViewById(R.id.btnCalanderD);
        Button discard = createRaffle.findViewById(R.id.btnDiscard);
        Button create = createRaffle.findViewById(R.id.btnCreate);

        final ArrayList<String> raffleTypes = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_types_array)));

        final ArrayAdapter<String> raffleTypesAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, raffleTypes);

        raffleType.setAdapter(raffleTypesAdapter);

        final ArrayList<String> numTickets = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_tickets_array)));

        final ArrayAdapter<String> numTicketsAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, numTickets);

        this.numTickets.setAdapter(numTicketsAdapter);

        addCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCoverImage.isChecked()) {
                    lblCoverImage.setVisibility(View.VISIBLE);
                }
                else {
                    lblCoverImage.setVisibility(View.INVISIBLE);
                }
            }
        });

        startDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(startDate, createRaffle);
                newFragment.show(fm, "datePicker");
            }
        });

        drawDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(drawDate, createRaffle);
                newFragment.show(fm, "datePicker");
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog fragment = new CustomAlertDialog(createRaffleObj);
                fragment.show(fm, "alert");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEntity();
            }
        });

        return createRaffle;
    }

    public void resetForm() {
        name.setText("");
        description.setText("");
        raffleType.setSelection(0);
        startDate.setText("");
        drawDate.setText("");
        location.setText("");
        ticketPrice.setText("");
        numTickets.setSelection(0);
        maxTickets.setText("");
        addCoverImage.setChecked(false);
    }

    public void createEntity() {

        RaffleType type =
                raffleType.getSelectedItemId() == 0 ? RaffleType.NORMAL_RAFFLE : RaffleType.MARGIN_RAFFLE;

        try {
            Raffle raffle = new Raffle(name.getText().toString(), description.getText().toString(), type,
                    startDate.getText().toString(), drawDate.getText().toString(), true,
                    location.getText().toString(), Float.parseFloat(ticketPrice.getText().toString()),
                    Integer.parseInt(numTickets.getSelectedItem().toString()), 0,
                    Integer.parseInt(maxTickets.getText().toString()));

            if (addCoverImage.isChecked()) {
                raffle.setRaffleCover(null);
            }

            if (RaffleTable.insert(db, raffle) != -1) {
                Log.d("###### Create Raffle","insert successful");
                Toast toast = Toast.makeText(context, R.string.create_raffle_success, Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Log.d("###### Create Raffle","insert error");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * base code reference: https://developer.android.com/guide/topics/ui/controls/pickers#java
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private TextView textView;
        private View view;

        DatePickerFragment(TextView textView, View view) {
            this.textView = textView;
            this.view = view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Log.d("**********", Utility.DATE_FORMAT.format(calendar.getTime()));
            textView.setText(Utility.DATE_FORMAT.format(calendar.getTime()));
        }
    }
}
