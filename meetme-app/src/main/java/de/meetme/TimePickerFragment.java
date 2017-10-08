package de.meetme;



import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by jahid on 12/10/15.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        EditText tv1=(EditText) getActivity().findViewById(R.id.editText13);
        int h;
        String ho;
        int m;
        String mi;
        if (Build.VERSION.SDK_INT >= 23 ) {
            h = view.getHour();
            m = view.getMinute();
            if (h<10){
                ho="0"+h;
            }else {
                ho=""+h;
            }
            if (m<10){
                mi="0"+m;
            }else {
                mi=""+m;
            }
            tv1.setText(ho+":"+mi);
        }else{
            h = view.getCurrentHour();
            m = view.getCurrentMinute();
            if (h<10){
                ho="0"+h;
            }else {
                ho=""+h;
            }
            if (m<10){
                mi="0"+m;
            }else {
                mi=""+m;
            }
            tv1.setText(ho+":"+mi);
        }

    }
}