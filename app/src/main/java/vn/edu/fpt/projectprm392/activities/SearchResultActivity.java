package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.adapters.SearchAdapter;
import vn.edu.fpt.projectprm392.models.Booking;
import vn.edu.fpt.projectprm392.models.District;
import vn.edu.fpt.projectprm392.models.Hotel;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView rcSearchResult;
    private String location;
    private Date startDate;
    private Date endDate;
    private FirebaseDatabase database;
    private DatabaseReference myRef, bookingRef;
    private int districtId;
    private Button btnBackToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Retrieve location, start date, and end date from intent extras
        location = getIntent().getStringExtra("location");
        startDate = (Date) getIntent().getSerializableExtra("startDate");
        endDate = (Date) getIntent().getSerializableExtra("endDate");

        // Instantiate RecyclerView and adapter
        rcSearchResult = findViewById(R.id.rc_search_result);
        rcSearchResult.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));


        // Get list of hotels from Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Hotels");
        bookingRef = database.getReference("Bookings");

        // Get id of district have name is same as location from Realtime Database and save to variable
        getDistrictsFromFirebase(new DistrictsCallback() {
            @Override
            public void onDistrictsLoaded(List<District> districts) {
                // Do something with the list of districts
                for (District district : districts) {
                    if (district.getName().equals(location)) {
                        districtId = district.getId();
                        getHotelsFromFirebase(districtId, new HotelsCallback() {
                            @Override
                            public void onHotelsLoaded(List<Hotel> hotels, String nameOfLocation, Date startDate, Date endDate) {
                                // Do something with the list of hotels
                                // If the list is empty, show a message to the user
                                SearchAdapter adapter = new SearchAdapter(hotels, nameOfLocation, startDate, endDate);
                                rcSearchResult.setAdapter(adapter);
                            }
                        }, location, startDate, endDate);
                        break;
                    }
                }
            }
        });

        // Back to Home
        btnBackToHome = findViewById(R.id.btn_backToHome);
        btnBackToHome.setOnClickListener(v -> {
            finish();
        });
    }


    // Interface for callback when list of districts is loaded from Firebase
    public interface DistrictsCallback {
        void onDistrictsLoaded(List<District> districts);
    }

    // Interface for callback when list of hotels is loaded from Firebase
    public interface HotelsCallback {
        void onHotelsLoaded(List<Hotel> hotels, String nameOfLocation, Date startDate, Date endDate);
    }

    // Get list districts from "Districts" reference in Realtime Database
    private void getDistrictsFromFirebase(DistrictsCallback callback) {
        DatabaseReference districtsRef = FirebaseDatabase.getInstance().getReference("Districts");
        final List<District> districtList = new ArrayList<>();
        districtsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot districtSnapshot : snapshot.getChildren()) {
                    District district = districtSnapshot.getValue(District.class);
                    districtList.add(district);
                }
                callback.onDistrictsLoaded(districtList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchResultActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get list of hotels from within a district and available on the given dates from Firebase
    private void getHotelsFromFirebase(int districtId, HotelsCallback callback, String nameOfLocation, Date dateIn, Date dateOut) {
        DatabaseReference hotelsRef = FirebaseDatabase.getInstance().getReference("Hotels");
        final List<Hotel> hotelList = new ArrayList<>();

        hotelsRef.orderByChild("districtId").equalTo(districtId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);
                    // check if bookingRef don't have any child
                    if (bookingRef != null) {
                        bookingRef.orderByChild("hotelId").equalTo(hotel.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<Booking> bookings = new ArrayList<>();
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    Booking booking = bookingSnapshot.getValue(Booking.class);
                                    bookings.add(booking);
                                }
                                if (checkAvailability(dateIn, dateOut, bookings)) {
                                    hotelList.add(hotel);
                                }
                                callback.onHotelsLoaded(hotelList, nameOfLocation, dateIn, dateOut);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(SearchResultActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchResultActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to check if a hotel is available on the given dates or that booking is canceled or checked-out
    private boolean checkAvailability(Date startDate, Date endDate, List<Booking> bookings) {
        for (Booking booking : bookings) {
            if ((startDate.after(booking.getIn_date()) && startDate.before(booking.getOut_date()) && (booking.getStatus().equals("Checked-in") || booking.getStatus().equals("On Going")))
                    || (endDate.after(booking.getIn_date()) && endDate.before(booking.getOut_date()) && (booking.getStatus().equals("Checked-in") || booking.getStatus().equals("On Going")))
                    || (startDate.before(booking.getIn_date()) && endDate.after(booking.getOut_date()) && (booking.getStatus().equals("Checked-in") || booking.getStatus().equals("On Going"))
                    || ((startDate.equals(booking.getIn_date()) || endDate.equals(booking.getOut_date())) && (booking.getStatus().equals("Checked-in") || booking.getStatus().equals("On Going")))
            )) {
                // The hotel is not available on the given dates
                return false;
            }
        }
        // The hotel is available on the given dates
        return true;
    }


}