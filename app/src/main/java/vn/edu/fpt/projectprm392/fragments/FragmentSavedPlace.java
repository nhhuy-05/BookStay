package vn.edu.fpt.projectprm392.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.fragments.PlaceModel.Place;
import vn.edu.fpt.projectprm392.fragments.PlaceModel.PlaceAdapter;

public class FragmentSavedPlace extends Fragment {

    private View mView;
    private RecyclerView rcv_place;
    private PlaceAdapter placeAdapter;
    public FragmentSavedPlace() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_saved_place, container, false);

        rcv_place =mView.findViewById(R.id.rcv_place);
        placeAdapter = new PlaceAdapter(mView.getContext());

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(mView.getContext(),RecyclerView.VERTICAL, false);
        rcv_place.setLayoutManager(linearLayoutManager);

        placeAdapter.setData(getListPlace());
        rcv_place.setAdapter(placeAdapter);

        return mView;
    }

    private List<Place> getListPlace(){
        List<Place> list = new ArrayList<>();
        list.add(new Place(R.drawable.vietnam, "Viet Nam"));
        list.add(new Place(R.drawable.kulalumpur, "Kula Lumpur"));
        list.add(new Place(R.drawable.canada, "Canada"));
        list.add(new Place(R.drawable.newyork, "New York"));
        list.add(new Place(R.drawable.seoul, "Seoul"));
        list.add(new Place(R.drawable.london, "London"));
        list.add(new Place(R.drawable.russia, "Russia"));
        list.add(new Place(R.drawable.beijing, "Beijing"));
        list.add(new Place(R.drawable.thailand, "ThaiLand"));
        return list;
    }
}