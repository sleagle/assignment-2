package au.edu.utas.sddhewa.assignment.ui.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import au.edu.utas.sddhewa.assignment.R;

public class ViewRaffle extends Fragment {

    private final ViewTabPageAdapter viewTabPageAdapter;
    private final SQLiteDatabase db;
    private final Bundle bundle;

    public ViewRaffle(SQLiteDatabase db, @NonNull FragmentManager fm, Context context, Bundle bundle) {
        this.db = db;
        this.bundle = bundle;
        viewTabPageAdapter = new ViewTabPageAdapter(fm, context, db, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_view, container, false);
        final ViewPager viewPager = home.findViewById(R.id.view_pager_V);
        viewPager.setAdapter(viewTabPageAdapter);
        TabLayout tabs = home.findViewById(R.id.view_tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("*************** tab", String.valueOf(tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        return home;

    }
}
