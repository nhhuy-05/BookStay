package vn.edu.fpt.projectprm392.fragments;

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
import vn.edu.fpt.projectprm392.adapters.ActiveBookingAdapter;
import vn.edu.fpt.projectprm392.adapters.HistoryBookingAdapter;
import vn.edu.fpt.projectprm392.models.Booking;
import vn.edu.fpt.projectprm392.models.Place;

public class FragmentBookings extends Fragment {

    private View mView;
    private RecyclerView rcv_ActiveBooking;
    private RecyclerView rcv_onGoingBooking;
    private ActiveBookingAdapter activeBookingAdapter;
    private HistoryBookingAdapter historyBookingAdapter;
    private List<Booking> activeBookings;
    private List<Booking> historyBookings;
    private FirebaseDatabase database;
    private DatabaseReference bookingRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bookings, container, false);

        // Check if user is logged in, if not, redirect to SignIn page
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getContext(), "Please sign in to view your bookings", Toast.LENGTH_SHORT).show();
        } else {
            rcv_ActiveBooking = mView.findViewById(R.id.rcv_ActiveBooking);
            rcv_onGoingBooking = mView.findViewById(R.id.rcv_HistoryBooking);

            // Database
            database = FirebaseDatabase.getInstance();
            bookingRef = database.getReference("Bookings");

            // Set layout manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), RecyclerView.VERTICAL, false);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mView.getContext(), RecyclerView.VERTICAL, false);
            rcv_ActiveBooking.setLayoutManager(linearLayoutManager);
            rcv_onGoingBooking.setLayoutManager(linearLayoutManager1);

            // Set adapter
            bookingRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    historyBookings = new ArrayList<>();
                    activeBookings = new ArrayList<>();
                    // get current date
                    Date currentDate = new Date();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("userId").getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            Booking booking = dataSnapshot.getValue(Booking.class);
                            // get booking that status is "Checked-out" or "Canceled" or out_date is before current date
                            if (booking.getStatus().equals("Checked-out") || booking.getStatus().equals("Canceled") || booking.getOut_date().before(currentDate)) {
                                historyBookings.add(booking);
                            }
                            // get booking that status is "Checked-in" or "On Going"
                            if (booking.getStatus().equals("Checked-in") || booking.getStatus().equals("On Going")) {
                                activeBookings.add(booking);
                            }
                        }
                    }
                    historyBookingAdapter = new HistoryBookingAdapter(historyBookings);
                    rcv_onGoingBooking.setAdapter(historyBookingAdapter);

                    activeBookingAdapter = new ActiveBookingAdapter(activeBookings);
                    rcv_ActiveBooking.setAdapter(activeBookingAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return mView;
    }
}