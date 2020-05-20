package au.edu.utas.sddhewa.assignment.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public final class Utility {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static final String KEY_SELECTED_RAFFLE = "SELECTED_RAFFLE";

    public static ArrayAdapter<String> getUserTitleAdapter(Context context, String[] array) {

        final ArrayList<String> titleList = new ArrayList<>(Arrays.asList(array));

        final ArrayAdapter<String> titleDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, titleList);

        return titleDropDown;
    }

    public static ArrayAdapter<String> getUserStateAdapter(Context context, String[] array) {

        final ArrayList<String> stateList = new ArrayList<>(Arrays.asList(array));

        final ArrayAdapter<String> stateDropDown = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, stateList);

        return stateDropDown;
    }
}
