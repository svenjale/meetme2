package de.meetme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by kortsch on 08.10.2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

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
        // Do something with the date chosen by the user
        EditText tv1= (EditText) getActivity().findViewById(R.id.editText11);
        int d = view.getDayOfMonth();
        tv1.setBackgroundColor(Color.GRAY);
        String da;
        if (d <10){
            da="0"+d;
        }else{
            da=""+d;
        }

        int m = view.getMonth()+1;
        String mo;
        if (m <10){
            mo="0"+m;
        }else{
            mo=""+m;
        }

        tv1.setText(da+"."+mo+"."+view.getYear());

    }
}
