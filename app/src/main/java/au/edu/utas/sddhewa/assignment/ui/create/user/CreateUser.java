package au.edu.utas.sddhewa.assignment.ui.create.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.edu.utas.sddhewa.assignment.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateUser extends Fragment {

    public CreateUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_user, container, false);
    }
}
