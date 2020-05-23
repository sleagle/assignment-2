package au.edu.utas.sddhewa.assignment.ui.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import au.edu.utas.sddhewa.assignment.R;

public class HomeTabPageAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_current, R.string.tab_past};
    private final Context context;
    private final SQLiteDatabase db;

    public HomeTabPageAdapter(@NonNull FragmentManager fm, Context context,
                              SQLiteDatabase db) {
        super(fm);
        this.context = context;
        this.db = db;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d("***************", String.valueOf(position));
        return PlaceholderFragment.newInstance(position, db);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
