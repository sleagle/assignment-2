package au.edu.utas.sddhewa.assignment.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import au.edu.utas.sddhewa.assignment.ui.alert.CustomErrorDialog;
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

        final Button editButton = root.findViewById(R.id.btnEdit);
        final Button deleteButton = root.findViewById(R.id.btnDelete);

        try {
            if (selectedTab == 0) {
                raffles = RaffleTable.selectCurrentRaffles(db);
                pageViewModel.setCurrentRaffles(getContext(), R.layout.list_current_raffles, raffles, selectedTab);
                Log.d("***************", "set the current raffles adapter");

            }
            else if (selectedTab == 1) {
                raffles= RaffleTable.selectPastRaffles(db);
                editButton.setEnabled(false);
                editButton.setAlpha(0.5f);
                editButton.setClickable(false);

                deleteButton.setEnabled(false);
                deleteButton.setAlpha(0.5f);
                deleteButton.setClickable(false);
                pageViewModel.setPastRaffles(getContext(), R.layout.list_past_raffles, raffles, selectedTab);
                Log.d("***************", "set the past raffles adapter");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Raffle> rafflesAdded = pageViewModel.getCurrentRafflesAdapter().getAddedRaffles();

                AlertType type = validateClick(rafflesAdded, true);
                if (type.equals(AlertType.VALID)) {

                }
                else {
                    CustomErrorDialog customErrorDialog = new CustomErrorDialog(type);
                    customErrorDialog.show(getActivity().getSupportFragmentManager(), "error");
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Raffle> rafflesAdded = pageViewModel.getCurrentRafflesAdapter().getAddedRaffles();
                List<String> notDeleted = new ArrayList<>();

                AlertType type = validateClick(rafflesAdded, false);

                if (type.equals(AlertType.VALID)) {

                    for (Raffle raffle : rafflesAdded) {
                        if (raffle.getTicketsSold() > 0) {
                            notDeleted.add(raffle.getName());
                        } else {
                            raffles.remove(raffle);
                            RaffleTable.deleteRaffle(db, raffle);
                        }
                    }

                    if (notDeleted.size() > 0 && notDeleted.size() != rafflesAdded.size()) {
                        displayCustomWarningDialog(notDeleted);
                        Utility.createDeleteSuccessToast(getContext(), pageViewModel);
                    }
                    else if (notDeleted.size() > 0) {
                        displayCustomWarningDialog(notDeleted);
                    }
                    else {
                        Utility.createDeleteSuccessToast(getContext(), pageViewModel);
                    }
                }
                else {
                    CustomErrorDialog customErrorDialog = new CustomErrorDialog(type);
                    customErrorDialog.show(getActivity().getSupportFragmentManager(), "error");
                }
                pageViewModel.getCurrentRafflesAdapter().clearAddedList(rafflesAdded);
            }
        });

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

                Log.d("#### rafflelist onclick", raffles.get(position).toString());
                Bundle bundle = new Bundle();
                bundle.putParcelable(Utility.KEY_SELECTED_RAFFLE, raffles.get(position));

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new ViewRaffle(db, getActivity().getSupportFragmentManager(),
                                        getContext(), bundle))
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
