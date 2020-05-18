package au.edu.utas.sddhewa.assignment.ui.create.raffle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.util.Utility;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateRaffle extends Fragment {

    private Context context;
    private SQLiteDatabase db;
    private FragmentManager fm;

    private TextView raffleName;
    private TextView raffleDescription;
    private Spinner raffleTypeSpinner;
    private TextView raffleStartDate;
    private TextView raffleDrawDate;
    private TextView raffleLocation;
    private TextView raffleTicketPrice;
    private Spinner raffleNumTicketsSpinner;
    private TextView raffleMaxTicketsPerPerson;


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

        raffleName = createRaffle.findViewById(R.id.txtName);
        raffleDescription = createRaffle.findViewById(R.id.txtDesc);
        raffleTypeSpinner = createRaffle.findViewById(R.id.raffle_type_spinner);
        raffleStartDate = createRaffle.findViewById(R.id.txtStartDate);
        raffleDrawDate = createRaffle.findViewById(R.id.txtCR_DrawDate);
        raffleLocation = createRaffle.findViewById(R.id.txtLocation);
        raffleTicketPrice = createRaffle.findViewById(R.id.txTicketPrice);
        raffleNumTicketsSpinner = createRaffle.findViewById(R.id.no_tickets_spinner);
        raffleMaxTicketsPerPerson = createRaffle.findViewById(R.id.txtMaxTickets);

        Button startDateSelect = createRaffle.findViewById(R.id.btnCalander);
        Button drawDateSelect = createRaffle.findViewById(R.id.btnCalanderD);

        final ArrayList<String> raffleTypes = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_types_array)));

        final ArrayAdapter<String> raffleTypesAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, raffleTypes);

        raffleTypeSpinner.setAdapter(raffleTypesAdapter);

        final ArrayList<String> numTickets = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_tickets_array)));

        final ArrayAdapter<String> numTicketsAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, numTickets);

        raffleNumTicketsSpinner.setAdapter(numTicketsAdapter);

        startDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(raffleStartDate, createRaffle);
                newFragment.show(fm, "datePicker");
            }
        });

        drawDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(raffleDrawDate, createRaffle);
                newFragment.show(fm, "datePicker");
            }
        });

        return createRaffle;
    }

    private void reset() {
        raffleName.setText("");
        raffleDescription.setText("");
        raffleTypeSpinner.setSelection(0);
        raffleStartDate.setText("");
        raffleDrawDate.setText("");
        raffleLocation.setText("");
        raffleTicketPrice.setText("");
        raffleNumTicketsSpinner.setSelection(0);
        raffleMaxTicketsPerPerson.setText("");
    }

    /*
     * base code reference: https://developer.android.com/guide/topics/ui/controls/pickers#java
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private TextView textView;
        private View view;

        public DatePickerFragment(TextView textView, View view) {
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
