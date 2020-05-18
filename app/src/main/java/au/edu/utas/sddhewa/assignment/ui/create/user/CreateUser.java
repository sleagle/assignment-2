package au.edu.utas.sddhewa.assignment.ui.create.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.ui.CustomAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.create.Create;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateUser extends Fragment implements Create {

    private final FragmentManager fm;
    private final Context context;
    private final SQLiteDatabase db;

    private Spinner titleSpinner;
    private TextView fName;
    private TextView lName;
    private TextView mobile;
    private TextView email;
    private TextView address;
    private TextView suburb;
    private TextView postCode;
    private Spinner stateSpinner;

    public CreateUser(Context context, SQLiteDatabase db, FragmentManager fm) {
        this.context = context;
        this.db = db;
        this.fm = fm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View createUser = inflater.inflate(R.layout.fragment_create_user, container, false);

        //textFields;
        fName = createUser.findViewById(R.id.txtFirstName);
        lName = createUser.findViewById(R.id.txtLastName);
        mobile = createUser.findViewById(R.id.txtMobile);
        email = createUser.findViewById(R.id.txtEmail);
        address = createUser.findViewById(R.id.txtAddress);
        suburb = createUser.findViewById(R.id.txtSuburb);
        postCode = createUser.findViewById(R.id.txtPostCode);

        final ArrayList<String> titleList = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.title_array)));

        final ArrayAdapter<String> titleDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, titleList);

        final ArrayList<String>statesList = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.states_array)));

        final ArrayAdapter<String> stateDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, statesList);

        titleSpinner = createUser.findViewById(R.id.title_spinner);
        titleSpinner.setAdapter(titleDropDown);

        stateSpinner = createUser.findViewById(R.id.state_spinner);
        stateSpinner.setAdapter(stateDropDown);

        Button createButton = createUser.findViewById(R.id.btnCreate);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEntity();
            }
        });

        Button discardButton = createUser.findViewById(R.id.btnDiscard);

        final CreateUser createUserObj = this;

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog fragment = new CustomAlertDialog(createUserObj);
                fragment.show(fm, "alert");
            }
        });

        return createUser;
    }

    public void resetForm() {
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

    public void createEntity() {

        Customer customer = new Customer(
                titleSpinner.getSelectedItem().toString(), fName.getText().toString(),
                lName.getText().toString(), Integer.parseInt(mobile.getText().toString()),
                email.getText().toString(), address.getText().toString(),
                suburb.getText().toString(), stateSpinner.getSelectedItem().toString(),
                Integer.parseInt(postCode.getText().toString())
        );
        Log.d("######", customer.toString());
        long cid = CustomerTable.insert(db, customer);

        if (cid != -1) {
            Log.d("###### Create Customer","insert successful");
            Toast toast = Toast.makeText(context, R.string.create_customer_success, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Log.d("###### Create Customer","insert error");
        }
        resetForm();
    }
}
