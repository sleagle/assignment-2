package au.edu.utas.sddhewa.assignment.ui.edit;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.CustomerTable;
import au.edu.utas.sddhewa.assignment.modal.Customer;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.FormInteraction;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomDismissAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.view.ViewRaffle;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class EditUser extends Fragment implements FormInteraction {

    private final SQLiteDatabase db;
    private final Bundle bundle;

    private Raffle raffle;
    private Customer customer;

    private Spinner titleSpinner;
    private TextView fName;
    private TextView lName;
    private TextView mobile;
    private TextView email;
    private TextView address;
    private TextView suburb;
    private TextView postCode;
    private Spinner stateSpinner;
    
    public EditUser(SQLiteDatabase db, Bundle bundle) {
        this.db = db;
        this.bundle = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View editCustomerView =
                inflater.inflate(R.layout.fragment_edit_user, container, false);

        customer = bundle.getParcelable(Utility.KEY_SELECTED_CUSTOMER);
        raffle = bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE);

        fName = editCustomerView.findViewById(R.id.txtFirstName_edit);
        fName.setEnabled(false);
        lName = editCustomerView.findViewById(R.id.txtLastName_edit);
        lName.setEnabled(false);
        mobile = editCustomerView.findViewById(R.id.txtMobile_edit);
        email = editCustomerView.findViewById(R.id.txtEmail_edit);
        address = editCustomerView.findViewById(R.id.txtAddress_edit);
        suburb = editCustomerView.findViewById(R.id.txtSuburb_edit);
        postCode = editCustomerView.findViewById(R.id.txtPostCode_edit);

        titleSpinner = editCustomerView.findViewById(R.id.title_spinner_edit);
        titleSpinner.setAdapter(Utility.getUserTitleAdapter(getContext(),
                getResources().getStringArray(R.array.title_array)));
        titleSpinner.setEnabled(false);

        stateSpinner = editCustomerView.findViewById(R.id.state_spinner_edit);
        stateSpinner.setAdapter(Utility.getUserStateAdapter(getContext(),
                getResources().getStringArray(R.array.states_array)));

        titleSpinner.setSelection(Utility.getTitleSelection(customer.getTitle()));
        fName.setText(customer.getFirstName());
        lName.setText(customer.getLastName());
        mobile.setText(String.valueOf(customer.getMobileNo()));
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        suburb.setText(customer.getSuburb());
        stateSpinner.setSelection(Utility.getStateSelection(customer.getState()));
        postCode.setText(String.valueOf(customer.getPostCode()));

        Button btnUpdate = editCustomerView.findViewById(R.id.btnCreate_edit);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrUpdateEntity();
            }
        });

        final EditUser editUser = this;
        Button btnDiscard = editCustomerView.findViewById(R.id.btnDiscard_edit);
        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDismissAlertDialog fragment = new CustomDismissAlertDialog(editUser);
                fragment.show(getActivity().getSupportFragmentManager(), "alert");
            }
        });

        return editCustomerView;
    }

    @Override
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

        bundle.putParcelable(Utility.KEY_SELECTED_RAFFLE, raffle);

        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container,
                        new ViewRaffle(db, getActivity().getSupportFragmentManager(),
                                getContext(), bundle, raffle.isActive()))
                .commit();
    }

    @Override
    public void createOrUpdateEntity() {

        customer.setMobileNo(Integer.parseInt(mobile.getText().toString()));
        customer.setEmail(email.getText().toString());
        customer.setAddress(address.getText().toString());
        customer.setSuburb(suburb.getText().toString());
        customer.setState(stateSpinner.getSelectedItem().toString());
        customer.setPostCode(Integer.parseInt(postCode.getText().toString()));

        long cid = CustomerTable.update(db, customer);

        if (cid != -1) {
            Log.d("###### Update Customer","Update successful");
            Toast toast = Toast.makeText(getContext(), R.string.update_customer_success, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Log.d("###### Update Customer","Update error");
        }

        resetForm();
    }
}
