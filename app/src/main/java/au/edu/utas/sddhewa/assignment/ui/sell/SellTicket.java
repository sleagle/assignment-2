package au.edu.utas.sddhewa.assignment.ui.sell;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTicketTable;
import au.edu.utas.sddhewa.assignment.db.table.TicketTable;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.modal.RaffleTicket;
import au.edu.utas.sddhewa.assignment.modal.Ticket;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomDismissAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomWarningDialog;
import au.edu.utas.sddhewa.assignment.ui.FormInteraction;
import au.edu.utas.sddhewa.assignment.ui.home.Home;
import au.edu.utas.sddhewa.assignment.util.Utility;
import au.edu.utas.sddhewa.assignment.util.AlertType;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellTicket extends Fragment implements FormInteraction {

    private Context context;
    private SQLiteDatabase db;
    private FragmentManager fragmentManager;

    private Raffle raffle;
    private Customer customer;
    private float total;
    private List<Integer> ticketsList;
    private int difference;

    private Spinner rafflesSpinner;
    private Spinner numTicketsSpinner;
    private CheckBox existingUserCbx;
    private Spinner customerSpinner;
    private TextView totalPrice;

    private Spinner titleSpinner;
    private TextView fName;
    private TextView lName;
    private TextView mobile;
    private TextView email;
    private TextView address;
    private TextView suburb;
    private TextView postCode;
    private Spinner stateSpinner;

    public SellTicket(Context context, SQLiteDatabase db, FragmentManager fragmentManager) {
        this.context = context;
        this.db = db;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View sellTickets = inflater.inflate(R.layout.fragment_sell_ticket, container, false);

        /* setting up the view components */
        rafflesSpinner = sellTickets.findViewById(R.id.raffles_spinner);
        totalPrice = sellTickets.findViewById(R.id.txtTotalPrice);

        numTicketsSpinner = sellTickets.findViewById(R.id.num_tickets_spinner);
        existingUserCbx = sellTickets.findViewById(R.id.cbx_existing_customer);

        final TextView lblCustomer = sellTickets.findViewById(R.id.lblCustomer_ST);
        lblCustomer.setVisibility(View.INVISIBLE);
        customerSpinner = sellTickets.findViewById(R.id.customer_spinner);
        customerSpinner.setVisibility(View.INVISIBLE);

        fName = sellTickets.findViewById(R.id.txtFirstName_ST);
        lName = sellTickets.findViewById(R.id.txtLastName_ST);
        mobile = sellTickets.findViewById(R.id.txtMobile_ST);
        email = sellTickets.findViewById(R.id.txtEmail_ST);
        address = sellTickets.findViewById(R.id.txtAddress_ST);
        suburb = sellTickets.findViewById(R.id.txtSuburb_ST);
        postCode = sellTickets.findViewById(R.id.txtPostCode_ST);

        titleSpinner = sellTickets.findViewById(R.id.title_spinner_ST);
        titleSpinner.setAdapter(Utility.getUserTitleAdapter(context,
                getResources().getStringArray(R.array.title_array)));

        stateSpinner = sellTickets.findViewById(R.id.state_spinner_ST);
        stateSpinner.setAdapter(Utility.getUserStateAdapter(context,
                getResources().getStringArray(R.array.states_array)));

        Button discardBtn = sellTickets.findViewById(R.id.btnDiscard_ST);
        final SellTicket ticket = this;

        discardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDismissAlertDialog fragment = new CustomDismissAlertDialog(ticket);
                fragment.show(fragmentManager, "alert");
            }
        });

        Button sellBtn = sellTickets.findViewById(R.id.btnSell);
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertType res = validateRequest();
                if (res.equals(AlertType.VALID)) {
                    createEntity();
                }
                else {
                    CustomWarningDialog fragment = new CustomWarningDialog(res, difference);
                    fragment.show(fragmentManager, "alert");
                }
            }
        });

        try {
            final List<Raffle> raffleList = RaffleTable.selectCurrentRaffles(db);
            final ArrayAdapter<Raffle> rafflesAdapter = new ArrayAdapter<>(
                    context, android.R.layout.simple_spinner_dropdown_item, raffleList);

            rafflesSpinner.setAdapter(rafflesAdapter);
            raffle = raffleList.size() >= 1 ? raffleList.get(0) : null;

            final List<Customer> customersList = CustomerTable.selectAll(db);
            final ArrayAdapter<Customer> customerArrayAdapter = new ArrayAdapter<>(
                    context, android.R.layout.simple_spinner_dropdown_item, customersList);

            customerSpinner.setAdapter(customerArrayAdapter);
            customer = customersList.size() >= 1 ? customersList.get(0) : null;

            rafflesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("#### tets", raffleList.get(position).toString());

                    raffle = raffleList.get(position);

                    int ticketsRemaining = raffle.getNoOfTickets() - raffle.getTicketsSold();

                    int ticketsToSell = Math.min(ticketsRemaining, raffle.getMaxTickets());

                    ticketsList = new ArrayList<>();

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

            /* num tickets spinner updating the total price */
            numTicketsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int numTickets = ticketsList.get(position);
                    total = raffle.getTicketPrice() * (float) numTickets;

                    totalPrice.setText(Utility.getFormattedPrice(total));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            existingUserCbx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (existingUserCbx.isChecked()) {
                        customerSpinner.setVisibility(View.VISIBLE);
                        lblCustomer.setVisibility(View.VISIBLE);
                        populateCustomerFields();
                    }
                    else {
                        customerSpinner.setVisibility(View.INVISIBLE);
                        lblCustomer.setVisibility(View.INVISIBLE);
                        clearCustomerFields();
                    }
                }
            });

            customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    customer = customersList.get(position);

                    if (existingUserCbx.isChecked()) {
                        populateCustomerFields();
                    }
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
        rafflesSpinner.setSelection(0);
        numTicketsSpinner.setSelection(0);
        totalPrice.setText("");

        clearCustomerFields();

        fragmentManager.beginTransaction().
                replace(R.id.fragment_container,
                        new Home(db, fragmentManager, context)).commit();
    }

    @Override
    public void createEntity() {

        RaffleTicket raffleTicket = new RaffleTicket(raffle.getRaffleId(),
                Integer.parseInt(numTicketsSpinner.getSelectedItem().toString()), total, new Date());
        if (existingUserCbx.isChecked()) {
            raffleTicket.setCustomerId(customer.getCustomerId());
        }
        else {
            raffleTicket.setCustomerId((createCustomer()));
        }
        raffleTicket.setRaffleTicketId(RaffleTicketTable.insert(db, raffleTicket));
        Log.d("###### CreateRaffleTket","insert successful");

        createNormalTicket(raffleTicket);
    }

    private AlertType validateRequest() {

        if (existingUserCbx.isChecked()) {
            try {
                List<RaffleTicket> tickets =
                        RaffleTicketTable.selectAllByCustomerId(db, customer.getCustomerId());
                if (tickets.size() == 0) {
                    return AlertType.VALID;
                } else {
                    int selected = Integer.parseInt(numTicketsSpinner.getSelectedItem().toString());
                    int count = 0;
                    for (RaffleTicket rt : tickets) {
                        count += rt.getNumTickets();
                    }

                    if (count == raffle.getMaxTickets()){
                        return AlertType.MAX_BUY_ALERT;
                    }

                    else if ((count + selected) > raffle.getMaxTickets()) {
                        difference = raffle.getMaxTickets() - count;
                        return AlertType.OVER_BUY_ALERT;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return AlertType.VALID;
    }

    private void createNormalTicket(RaffleTicket raffleTicket) {

        int number = raffle.getTicketsSold();

        for (int i = 1 ; i <= raffleTicket.getNumTickets();  i++) {

            number++;
            String ticketNum = raffle.getName().substring(0, 3).toUpperCase() + number;

            TicketTable.insert(db, new Ticket(ticketNum, raffleTicket.getRaffleTicketId()));
        }
        Log.d("###### Create ticket","insert successful");

        raffle.setTicketsSold(number);

        RaffleTable.update(db, raffle);
        Log.d("###### updated raffle","update successful");

        Toast toast = Toast.makeText(context, R.string.sell_ticket_success, Toast.LENGTH_LONG);
        toast.show();
        resetForm();
    }

    private long createCustomer() {

        Customer customer = new Customer(
                titleSpinner.getSelectedItem().toString(), fName.getText().toString(),
                lName.getText().toString(), Integer.parseInt(mobile.getText().toString()),
                email.getText().toString(), address.getText().toString(),
                suburb.getText().toString(), stateSpinner.getSelectedItem().toString(),
                Integer.parseInt(postCode.getText().toString())
        );

        return CustomerTable.insert(db, customer);
    }

    private void populateCustomerFields() {
        titleSpinner.setSelection(Utility.getTitleSelection(customer.getTitle()));
        fName.setText(customer.getFirstName());
        lName.setText(customer.getLastName());
        mobile.setText(String.valueOf(customer.getMobileNo()));
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        suburb.setText(customer.getSuburb());
        stateSpinner.setSelection(Utility.getStateSelection(customer.getState()));
        postCode.setText(String.valueOf(customer.getPostCode()));
    }

    private void clearCustomerFields() {
        titleSpinner.setSelection(0);
        fName.setText("");
        lName.setText("");
        mobile.setText("");
        email.setText("");
        address.setText("");
        suburb.setText("");
        stateSpinner.setSelection(0);
        postCode.setText("");
    }
}
