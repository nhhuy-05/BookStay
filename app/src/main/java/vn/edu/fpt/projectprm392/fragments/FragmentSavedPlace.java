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
import vn.edu.fpt.projectprm392.adapters.SavedPlaceAdapter;
import vn.edu.fpt.projectprm392.models.Place;

public class FragmentSavedPlace extends Fragment {

    private View mView;
    private RecyclerView rcv_place;
    private SavedPlaceAdapter placeAdapter;
    public FragmentSavedPlace() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_saved_place, container, false);

        rcv_place =mView.findViewById(R.id.rcv_place);
        placeAdapter = new SavedPlaceAdapter(mView.getContext());

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(mView.getContext(),RecyclerView.VERTICAL, false);
        rcv_place.setLayoutManager(linearLayoutManager);

        placeAdapter.setData(getListPlace());
        rcv_place.setAdapter(placeAdapter);

        return mView;
    }


    private List<Place> getListPlace(){
        List<Place> list = new ArrayList<>();
        list.add(new Place(R.drawable.img_vietnam, "Viet Nam"));
        list.add(new Place(R.drawable.img_vietnam, "Kula Lumpur"));
        list.add(new Place(R.drawable.img_vietnam, "Canada"));
        list.add(new Place(R.drawable.img_vietnam, "New York"));
        list.add(new Place(R.drawable.img_vietnam, "Seoul"));
        list.add(new Place(R.drawable.img_vietnam, "London"));
        list.add(new Place(R.drawable.img_vietnam, "Russia"));
        list.add(new Place(R.drawable.img_vietnam, "Beijing"));
        list.add(new Place(R.drawable.img_vietnam, "ThaiLand"));
        return list;
    }
}