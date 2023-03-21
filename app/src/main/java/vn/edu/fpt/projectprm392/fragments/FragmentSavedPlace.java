package vn.edu.fpt.projectprm392.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.SignInActivity;
import vn.edu.fpt.projectprm392.adapters.SavedPlaceAdapter;
import vn.edu.fpt.projectprm392.models.Hotel;
import vn.edu.fpt.projectprm392.models.Place;

public class FragmentSavedPlace extends Fragment {

    private View mView;
    private RecyclerView rcv_saved_hotel;
    private SavedPlaceAdapter savedHotelsAdapter;
    private List<Hotel> savedHotels;
    private List<Date> startDates, endDates;
    private List<String> nameOfLocation;
    private FirebaseDatabase database;
    private DatabaseReference savedHotelRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_saved_place, container, false);
        // Check if user is logged in, if not, redirect to SignIn page
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getContext(), "Please sign in to view your saved places", Toast.LENGTH_SHORT).show();
        } else {

            // Instantiate RecyclerView and adapter
            rcv_saved_hotel = mView.findViewById(R.id.rcv_saved_hotel);
            rcv_saved_hotel.setLayoutManager(new LinearLayoutManager(getContext()));

            // Database
            database = FirebaseDatabase.getInstance();
            savedHotelRef = database.getReference("SavedHotels").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            // Get list of saved hotels from SavedHotels Reference corresponding to the currently logged in user, in SavedHotels Reference, each child have EndDate and StartDate,
            // location is the name of location, and hotelID is the key of each saved hotel.
            savedHotelRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    savedHotels = new ArrayList<>();
                    startDates = new ArrayList<>();
                    endDates = new ArrayList<>();
                    nameOfLocation = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("userId").getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            savedHotels.add(ds.child("hotel").getValue(Hotel.class));
                            startDates.add(ds.child("startDate").getValue(Date.class));
                            endDates.add(ds.child("endDate").getValue(Date.class));
                            nameOfLocation.add(ds.child("location").getValue(String.class));
                        }
                    }
                    savedHotelsAdapter = new SavedPlaceAdapter(savedHotels, nameOfLocation, startDates, endDates);
                    rcv_saved_hotel.setAdapter(savedHotelsAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return mView;
    }
}
