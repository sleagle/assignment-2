package au.edu.utas.sddhewa.assignment.ui.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import au.edu.utas.sddhewa.assignment.R;

public class ViewTabPageAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_current, R.string.tab_tickets};
    private final Context context;
    private final SQLiteDatabase db;
    private final Bundle bundle;

    public ViewTabPageAdapter(@NonNull FragmentManager fm, Context context,
                              SQLiteDatabase db, Bundle bundle) {
        super(fm);
        this.context = context;
        this.db = db;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RaffleDetail(bundle);
            case 1:
                return new TicketsSold(context, db, bundle);
            default:
                return null;
        }
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
