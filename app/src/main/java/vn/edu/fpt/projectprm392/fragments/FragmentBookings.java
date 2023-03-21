package vn.edu.fpt.projectprm392.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.adapters.ActiveBookingAdapter;
import vn.edu.fpt.projectprm392.adapters.OngoingBookingAdapter;
import vn.edu.fpt.projectprm392.models.Place;

public class FragmentBookings extends Fragment {

    private View mView;
    private RecyclerView rcv_ActiveBooking;
    private RecyclerView rcv_onGoingBooking;
    private ActiveBookingAdapter activeBookingAdapter;
    private OngoingBookingAdapter ongoingBookingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bookings, container, false);
        rcv_ActiveBooking = mView.findViewById(R.id.rcv_ActiveBooking);
        rcv_onGoingBooking = mView.findViewById(R.id.rcv_OngoingBooking);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mView.getContext(), RecyclerView.VERTICAL, false);
        rcv_ActiveBooking.setLayoutManager(linearLayoutManager);
        rcv_onGoingBooking.setLayoutManager(linearLayoutManager1);

        activeBookingAdapter = new ActiveBookingAdapter(getListPlaceActive());
        ongoingBookingAdapter = new OngoingBookingAdapter(getListPlaceOngoing());
        rcv_ActiveBooking.setAdapter(activeBookingAdapter);
        rcv_onGoingBooking.setAdapter(ongoingBookingAdapter);

        return mView;
    }

    private List<Place> getListPlaceActive(){
        List<Place> list = new ArrayList<>();
        list.add(new Place(R.drawable.img_vietnam, "Vietnam", "Hanoi", "Hotel", "Check-in"));
        list.add(new Place(R.drawable.img_vietnam, "Indonesia", "Kuala Lumpur", "HomeStay", "Check-in"));
        list.add(new Place(R.drawable.img_vietnam, "Canada", "Vancouver", "Hotel", "Check-in"));
        list.add(new Place(R.drawable.img_vietnam, "New York", "Illinois", "Hotel", "Check-in"));
        list.add(new Place(R.drawable.img_vietnam, "South Korea", "Seoul", "HomeStay", "Check-in"));
        return list;
    }
    private List<Place> getListPlaceOngoing(){
        List<Place> list = new ArrayList<>();
        list.add(new Place(R.drawable.img_vietnam, "Vietnam", "Hanoi", "Hotel", "Check-Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Indonesia", "Kuala Lumpur", "HomeStay", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Canada", "Vancouver", "Hotel", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "New York", "Illinois", "Hotel", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "South Korea", "Seoul", "HomeStay", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "England", "London", "Hotel", "Ongoing"));
        list.add(new Place(R.drawable.img_vietnam, "Russia", "Moscow", "HomeStay", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "China", "Beijing", "Hotel", "Waiting"));
        list.add(new Place(R.drawable.img_vietnam, "Thailand", "Bangkok", "Hotel", "Ongoing"));
        return list;
    }
}