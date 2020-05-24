package au.edu.utas.sddhewa.assignment.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.ui.home.PageViewModel;

public final class Utility {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static final String KEY_SELECTED_RAFFLE = "SELECTED_RAFFLE";

    public static final String KEY_SELECTED_RAFFLE_TICKET = "SELECTED_RAFFLE_TICKET";

    public static String getFormattedPrice(float price) {
        return String.format("$ %.2f", price);
    }

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

    public static int getTitleSelection(String title) {
        switch (title) {
            case "Dr":
                return 0;
            case "Mr":
                return 1;
            case "Miss":
                return 2;
            case "Mrs":
                return 3;
            case "Ms":
                return 4;
            default:
                return -1;
        }
    }

    public static int getStateSelection(String state) {
        switch (state) {
            case "ACT":
                return 0;
            case "NSW":
                return 1;
            case "NT":
                return 2;
            case "SA":
                return 3;
            case "TAS":
                return 4;
            case "VIC":
                return 5;
            case "WA":
                return 6;
            default:
                return -1;
        }
    }

    public static void createDeleteSuccessToast(Context context, PageViewModel pageViewModel) {
        pageViewModel.getCurrentRafflesAdapter().notifyDataSetChanged();
        Toast toast = Toast.makeText(context, R.string.delete_raffle_success, Toast.LENGTH_LONG);
        toast.show();
    }
}
