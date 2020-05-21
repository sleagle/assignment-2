package au.edu.utas.sddhewa.assignment.ui.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import au.edu.utas.sddhewa.assignment.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private final HomeTabPageAdapter adapter;
    private final SQLiteDatabase db;

    public Home(SQLiteDatabase db, @NonNull FragmentManager fm, Context context) {
        this.db = db;
        adapter = new HomeTabPageAdapter(fm, context, db);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        final ViewPager viewPager = home.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabs = home.findViewById(R.id.home_tabs);
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
