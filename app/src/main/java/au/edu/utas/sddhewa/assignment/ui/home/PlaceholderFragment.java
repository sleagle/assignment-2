package au.edu.utas.sddhewa.assignment.ui.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.util.ArrayList;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;

public class PlaceholderFragment extends Fragment {

    private final Context context;
    private final SQLiteDatabase db;
    private int selectedTab;
    private PageViewModel pageViewModel;

    static Fragment newInstance(int i, Context context, SQLiteDatabase db) {

        PlaceholderFragment p = new PlaceholderFragment(context, db, i);
        return p;
    }

    private PlaceholderFragment(Context context, SQLiteDatabase db, int i) {
        this.context = context;
        this.db = db;
        this.selectedTab = i;
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
                ArrayList<Raffle> raffles = RaffleTable.selectCurrentRaffles(db);
                pageViewModel.setCurrentRaffles(context, R.layout.list_current_raffles, raffles, selectedTab);
                Log.d("***************", "set the current raffles adapter");
            }
            if (selectedTab == 1) {
                ArrayList<Raffle> raffles = RaffleTable.selectPastRaffles(db);
                pageViewModel.setPastRaffles(context, R.layout.list_past_raffles, raffles, selectedTab);
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

        return root;
    }
}
