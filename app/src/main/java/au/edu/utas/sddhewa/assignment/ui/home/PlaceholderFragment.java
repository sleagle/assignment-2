package au.edu.utas.sddhewa.assignment.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomWarningDialog;
import au.edu.utas.sddhewa.assignment.ui.view.ViewRaffle;
import au.edu.utas.sddhewa.assignment.util.AlertType;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class PlaceholderFragment extends Fragment {

    //private final Context context;
    private final SQLiteDatabase db;
    private int selectedTab;
    private PageViewModel pageViewModel;

    //private ArrayList<Raffle> rafflesCopy;
    private ArrayList<Raffle> raffles;

    static Fragment newInstance(int i, SQLiteDatabase db) {

        return new PlaceholderFragment(db, i);
    }

    private PlaceholderFragment(SQLiteDatabase db, int i) {
        //this.context = context;
        this.db = db;
        this.selectedTab = i;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            selectedTab = savedInstanceState.getInt("selectedTab");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        Log.d("***************SELECTED", String.valueOf(selectedTab));
        pageViewModel.setIndex(selectedTab);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab_home, container, false);
        Log.d("***************", "Inflated view");
        final ListView tabListView = root.findViewById(R.id.rafflesList);

        try {
            if (selectedTab == 0) {
                raffles = RaffleTable.selectCurrentRaffles(db);
                pageViewModel.setCurrentRaffles(getContext(), R.layout.list_current_raffles, raffles, selectedTab);
                Log.d("***************", "set the current raffles adapter");

            }
            else if (selectedTab == 1) {
                raffles= RaffleTable.selectPastRaffles(db);
                pageViewModel.setPastRaffles(getContext(), R.layout.list_past_raffles, raffles, selectedTab, db);
                Log.d("***************", "set the past raffles adapter");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pageViewModel.getRaffleList().observe(getViewLifecycleOwner(), new Observer<ArrayAdapter<Raffle>>() {
            @Override
            public void onChanged(@Nullable ArrayAdapter<Raffle> adapter) {
                Log.d("***************", "on change adapter");
                tabListView.setAdapter(adapter);

            }
        });

        tabListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("#### selectedTab: ", ""+selectedTab);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Utility.KEY_SELECTED_RAFFLE, raffles.get(position));
                boolean isCurrent = selectedTab == 0;

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new ViewRaffle(db, getActivity().getSupportFragmentManager(),
                                        getContext(), bundle, isCurrent))
                        .addToBackStack(null).commit();
            }
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedTab", selectedTab);
    }

    private AlertType validateClick(List<Raffle> selectedRafflesList, boolean isEdit) {

       if (selectedRafflesList.size() == 0) {
           return AlertType.NON_SELECT;
        }
       else if (isEdit && selectedRafflesList.size() > 1) {
            return AlertType.MULTI_SELECT_ERROR;
        }
        return AlertType.VALID;
    }

    private void displayCustomWarningDialog(List<String> list) {
        CustomWarningDialog customWarningDialog = new CustomWarningDialog(AlertType.NOT_DELETED_MULTI);
        customWarningDialog.setNotDeletedList(list);
        customWarningDialog.show(getActivity().getSupportFragmentManager(), "warning");
    }
}
