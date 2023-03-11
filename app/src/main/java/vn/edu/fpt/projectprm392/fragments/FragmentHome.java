package vn.edu.fpt.projectprm392.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.fpt.projectprm392.R;

public class FragmentHome extends Fragment {

    private TextView tvDateStart, tvDateEnd;
    private CalendarView clvPicker;
    private Button btnSearchRoom, btnDone;
    private ConstraintLayout layout_pickerDate;
    private boolean isStartDateSelected;
    private long minDate = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvDateStart = view.findViewById(R.id.tv_dateStart);
        tvDateEnd = view.findViewById(R.id.tv_dateEnd);
        clvPicker = view.findViewById(R.id.clv_picker);
        layout_pickerDate = view.findViewById(R.id.layout_pickerDate);
        btnSearchRoom = view.findViewById(R.id.btn_searchRoom);
        btnDone = view.findViewById(R.id.btn_done);
        layout_pickerDate.setVisibility(View.GONE);

        // Set minimum date to today's date
        minDate = System.currentTimeMillis();
        clvPicker.setMinDate(minDate);

        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDateSelected = true;
                layout_pickerDate.setVisibility(View.VISIBLE);
            }
        });

        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDateSelected = false;
                layout_pickerDate.setVisibility(View.VISIBLE);
                // Set minimum date to the date selected in the start date TextView
                clvPicker.setMinDate(minDate);
            }
        });

        clvPicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                if (isStartDateSelected) {
                    tvDateStart.setText(date);
                    // Store the selected start date as the minimum date for the end date
                    minDate = clvPicker.getDate();
                } else {
                    tvDateEnd.setText(date);
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_pickerDate.setVisibility(View.GONE);
            }
        });
        return view;
    }
}