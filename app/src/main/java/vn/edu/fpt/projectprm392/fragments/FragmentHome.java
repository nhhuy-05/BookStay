package vn.edu.fpt.projectprm392.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.SearchResultActivity;
import vn.edu.fpt.projectprm392.databinding.ActivityMainBinding;

public class FragmentHome extends Fragment {

    private EditText edtSearchLocation;
    private TextView tvDateStart, tvDateEnd;
    private CalendarView clvPicker;
    private Button btnSearchRoom, btnDone;
    private ConstraintLayout layout_pickerDate;
    private boolean isStartDateSelected;
    private long minDate = 0;

    private FirebaseDatabase database;

    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initiate the views
        edtSearchLocation = view.findViewById(R.id.edt_searchLocation);
        tvDateStart = view.findViewById(R.id.tv_dateStart);
        tvDateEnd = view.findViewById(R.id.tv_dateEnd);
        clvPicker = view.findViewById(R.id.clv_picker);
        layout_pickerDate = view.findViewById(R.id.layout_pickerDate);
        btnSearchRoom = view.findViewById(R.id.btn_searchRoom);
        btnDone = view.findViewById(R.id.btn_done);

        // Set visibility of the date picker to GONE
        layout_pickerDate.setVisibility(View.GONE);

        // Set minimum date to today's date
        minDate = System.currentTimeMillis();
        clvPicker.setMinDate(minDate);

        // Set the date selected in the start date TextView as the minimum date for the end date
        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDateSelected = true;
                layout_pickerDate.setVisibility(View.VISIBLE);
                // Set Color to the selected TextView
                tvDateStart.setBackground(getResources().getDrawable(R.color.light_gray));
                tvDateEnd.setBackground(getResources().getDrawable(R.color.second_background));
            }
        });
        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDateSelected = false;
                layout_pickerDate.setVisibility(View.VISIBLE);
                // Set minimum date to the date selected in the start date TextView
                clvPicker.setMinDate(minDate);
                tvDateEnd.setBackground(getResources().getDrawable(R.color.light_gray));
                tvDateStart.setBackground(getResources().getDrawable(R.color.second_background));
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
                tvDateEnd.setBackground(getResources().getDrawable(R.color.second_background));
                tvDateStart.setBackground(getResources().getDrawable(R.color.second_background));
            }
        });
        btnSearchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = edtSearchLocation.getText().toString();
                String dateStart = tvDateStart.getText().toString();
                String dateEnd = tvDateEnd.getText().toString();
                if (dateStart.equals("Start Date") || dateEnd.equals("End Date") || location.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    try {
                        Date startDate = sdf.parse(dateStart);
                        Date endDate = sdf.parse(dateEnd);
                        if (startDate.after(endDate)) {
                            Toast.makeText(getContext(), "Start date must be before end date", Toast.LENGTH_SHORT).show();
                        } else {
                            // Create an intent to start the SearchResultActivity
                            Intent intent = new Intent(getActivity(), SearchResultActivity.class);


                            // Pass any data you need to the SearchResultActivity using extras
                            intent.putExtra("startDate", startDate);
                            intent.putExtra("endDate", endDate);

                            // Start the SearchResultActivity
                            startActivity(intent);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }
}