package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    private DatabaseReference myRef;

    private int districtId;


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

        // Get id of district have name is same as location from Realtime Database and save to variable
        getDistrictsFromFirebase(new DistrictsCallback() {
            @Override
            public void onDistrictsLoaded(List<District> districts) {
                // Do something with the list of districts
                for (District district : districts) {
                    if (district.getName().equals(location)) {
                        districtId = district.getId();
                        getHotelsFromFirebase(districtId,new HotelsCallback() {
                            @Override
                            public void onHotelsLoaded(List<Hotel> hotels,String nameOfLocation) {
                                // Do something with the list of hotels
                                SearchAdapter adapter = new SearchAdapter(hotels,nameOfLocation);
                                rcSearchResult.setAdapter(adapter);
                            }
                        },location);
                        break;
                    }
                }
            }
        });
    }


    // Interface for callback when list of districts is loaded from Firebase
    public interface DistrictsCallback {
        void onDistrictsLoaded(List<District> districts);
    }

    // Interface for callback when list of hotels is loaded from Firebase
    public interface HotelsCallback {
        void onHotelsLoaded(List<Hotel> hotels,String nameOfLocation);
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
    private void getHotelsFromFirebase(int districtId, HotelsCallback callback,String nameOfLocation) {
        DatabaseReference hotelsRef = FirebaseDatabase.getInstance().getReference("Hotels");
        final List<Hotel> hotelList = new ArrayList<>();

        hotelsRef.orderByChild("districtId").equalTo(districtId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);
                    hotelList.add(hotel);
                }
                callback.onHotelsLoaded(hotelList,nameOfLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchResultActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to check if a hotel is available on the given dates
    private boolean checkAvailability(Date startDate, Date endDate, List<Booking> bookings) {
        for (Booking booking : bookings) {
            if ((startDate.before(booking.getOut_date()) && endDate.after(booking.getIn_date())) ||
                    (startDate.equals(booking.getIn_date()) && endDate.equals(booking.getOut_date()))) {
                // The hotel is not available on the given dates
                return false;
            }
        }
        // The hotel is available on the given dates
        return true;
    }


}