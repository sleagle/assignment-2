package au.edu.utas.sddhewa.assignment.ui.create.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import au.edu.utas.sddhewa.assignment.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateUser extends Fragment {

    private Context context;

    public CreateUser(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View createUser = inflater.inflate(R.layout.fragment_create_user, container, false);

        final ArrayList<String> titleList = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.title_array)));

        ArrayAdapter<String> titleDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, titleList);

        final ArrayList<String>statesList = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.states_array)));

        ArrayAdapter<String> stateDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, statesList);

        Spinner titleSpinner = createUser.findViewById(R.id.title_spinner);
        titleSpinner.setAdapter(titleDropDown);

        Spinner stateSpinner = createUser.findViewById(R.id.state_spinner);
        stateSpinner.setAdapter(stateDropDown);

        return createUser;
    }
}
