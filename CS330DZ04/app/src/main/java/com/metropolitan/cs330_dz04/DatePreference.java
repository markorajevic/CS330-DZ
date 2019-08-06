package com.metropolitan.cs330_dz04;

import android.content.Context;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePreference extends DialogPreference {
    private int lastDay = 18;
    private int lastMonth = 3;
    private int lastYear = 2018;
    private DatePicker picker = null;

    public DatePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        picker = new DatePicker(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            picker.setCalendarViewShown(false);
        }
        return (picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.updateDate(lastYear, lastMonth, lastDay);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastYear = picker.getYear();
            lastMonth = picker.getMonth();
            lastDay = picker.getDayOfMonth();

            String dateval = String.valueOf(lastYear) + "-"
                    + String.valueOf(lastMonth) + "-"
                    + String.valueOf(lastDay);

            if (callChangeListener(dateval)) {
                persistString(dateval);
            }

            Toast.makeText(getContext(), "Datum: " + dateval, Toast.LENGTH_SHORT).show();//Stampanje odabranog datuma
        }
    }
}