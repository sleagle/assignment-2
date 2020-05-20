package au.edu.utas.sddhewa.assignment.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import au.edu.utas.sddhewa.assignment.util.Utility;

/*
 * base code reference: https://developer.android.com/guide/topics/ui/controls/pickers#java
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView textView;
    private View view;

    public DatePickerFragment(TextView textView, View view) {
        this.textView = textView;
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        textView.setText(Utility.DATE_FORMAT.format(calendar.getTime()));
    }
}
