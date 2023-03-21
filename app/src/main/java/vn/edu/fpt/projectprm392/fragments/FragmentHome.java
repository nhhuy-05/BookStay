package vn.edu.fpt.projectprm392.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.MainActivity;
import vn.edu.fpt.projectprm392.activities.SearchResultActivity;
import vn.edu.fpt.projectprm392.adapters.DistrictAdapter;
import vn.edu.fpt.projectprm392.adapters.OutStandingRoomAdapter;
import vn.edu.fpt.projectprm392.models.Booking;
import vn.edu.fpt.projectprm392.models.District;
import vn.edu.fpt.projectprm392.models.Hotel;
import vn.edu.fpt.projectprm392.models.PaymentMethod;

public class FragmentHome extends Fragment {

    private EditText edtSearchLocation;
    private TextView tvDateStart, tvDateEnd;
    private CalendarView clvPicker;
    private Button btnSearchRoom, btnDone;
    private ConstraintLayout layout_pickerDate;
    private boolean isStartDateSelected;
    private long minDate = 0;
    private RecyclerView rvSearchResult, rcv_outstanding;

    private FirebaseDatabase database;
    private DatabaseReference bookingRef, hotelRef, districtRef;

    private HashMap<Integer, Integer> outstandingHotels;
    private List<Hotel> top3Hotels;
    private List<Integer> listNumberOfBookings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // get views
        edtSearchLocation = view.findViewById(R.id.edt_searchLocation);
        tvDateStart = view.findViewById(R.id.tv_dateStart);
        tvDateEnd = view.findViewById(R.id.tv_dateEnd);
        clvPicker = view.findViewById(R.id.clv_picker);
        layout_pickerDate = view.findViewById(R.id.layout_pickerDate);
        btnSearchRoom = view.findViewById(R.id.btn_searchRoom);
        btnDone = view.findViewById(R.id.btn_done);
        rvSearchResult = view.findViewById(R.id.rv_searchResult);
        rcv_outstanding = view.findViewById(R.id.rcv_outstanding);

        // set up recycler view
        outstandingHotels = new HashMap<>();
        top3Hotels = new ArrayList<>();
        listNumberOfBookings = new ArrayList<>();

        // database
        database = FirebaseDatabase.getInstance();
        bookingRef = database.getReference("Bookings");
        hotelRef = database.getReference("Hotels");
        districtRef = database.getReference("Districts");

        // get 3 hotels with number of booking is highest
        getOutstandingHotels();

        // Set visibility of some view to GONE
        layout_pickerDate.setVisibility(View.GONE);
        rvSearchResult.setVisibility(View.GONE);

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

        // Set the date selected in the end date TextView as the minimum date for the start date
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

        // Set the selected date to the TextView
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

        // Set the visibility of the date picker to GONE when the done button is clicked
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_pickerDate.setVisibility(View.GONE);
                tvDateEnd.setBackground(getResources().getDrawable(R.color.second_background));
                tvDateStart.setBackground(getResources().getDrawable(R.color.second_background));
            }
        });

        // Search for the districts that match the search text
        edtSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rvSearchResult.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // call searchDistricts method with the current search text
                searchDistricts(s.toString());
                if (s.toString().isEmpty()) {
                    rvSearchResult.setVisibility(View.GONE);
                } else {
                    rvSearchResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Search for the rooms that match the search criteria
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

                            // Pass the search criteria to the SearchResultActivity
                            intent.putExtra("location", location);
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

    // Get 3 hotels with number of booking is highest
    private void getOutstandingHotels() {
        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<Integer, Integer> outstandingHotels = new HashMap<>();
                List<Integer> topHotelIds = new ArrayList<>();
                int count = 0;
                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);
                    int hotelId = booking.getHotelId();
                    if (outstandingHotels.containsKey(hotelId)) {
                        int bookingCount = outstandingHotels.get(hotelId);
                        outstandingHotels.put(hotelId, bookingCount + 1);
                    } else {
                        outstandingHotels.put(hotelId, 1);
                    }
                }
                // Sort the hotels by number of booking
                List<Map.Entry<Integer, Integer>> list = new ArrayList<>(outstandingHotels.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });

                // Get the top 3 hotels
                int topHotelCount = Math.min(list.size(), 3);
                for (int i = 0; i < topHotelCount; i++) {
                    int hotelId = list.get(i).getKey();
                    int numberOfBooking = list.get(i).getValue();
                    topHotelIds.add(hotelId);
                    getHotel(hotelId,topHotelIds,numberOfBooking);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getHotel(int hotelId, final List<Integer> topHotelIds, final int numberOfBooking) {
        FirebaseDatabase.getInstance().getReference("Hotels").child(String.valueOf(hotelId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Hotel hotel = dataSnapshot.getValue(Hotel.class);
                // Add the retrieved hotel to the list of top hotels
                top3Hotels.add(hotel);
                listNumberOfBookings.add(numberOfBooking);
                // Check if all top hotels have been retrieved
                if (top3Hotels.size() == topHotelIds.size()) {
                    // If all top hotels have been retrieved, update the UI
                    updateOutStandingUI(top3Hotels,listNumberOfBookings);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateOutStandingUI(List<Hotel> hotels,List<Integer> numberOfBooking) {
        OutStandingRoomAdapter adapter = new OutStandingRoomAdapter(hotels,numberOfBooking);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        rcv_outstanding.setLayoutManager(linearLayoutManager);
        rcv_outstanding.setAdapter(adapter);
    }


    // Search for districts whose name starts with the search text
    private void searchDistricts(String searchText) {
        DatabaseReference districtsRef = FirebaseDatabase.getInstance().getReference("Districts");

        // Search for districts whose name starts with the search text without case sensitivity
        Query query = districtsRef.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<District> districts = new ArrayList<>();
                for (DataSnapshot districtSnapshot : dataSnapshot.getChildren()) {
                    District district = districtSnapshot.getValue(District.class);
                    districts.add(district);
                }
                // update UI with the list of districts
                updateUI(districts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle error
            }
        });

    }

    // Update the RecyclerView with the list of districts
    private void updateUI(List<District> districts) {
        DistrictAdapter adapter = new DistrictAdapter(districts);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchResult.setAdapter(adapter);
        adapter.setOnItemClickListener(new DistrictAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(District district) {
                // Update search box with district name
                edtSearchLocation.setText(district.getName());

                // Hide RecyclerView
                rvSearchResult.setVisibility(View.GONE);

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSearchLocation.getWindowToken(), 0);
            }
        });
    }
}