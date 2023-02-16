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
import vn.edu.fpt.projectprm392.fragments.ItemModel.Item;
import vn.edu.fpt.projectprm392.fragments.ItemModel.ItemAdapter;

public class FragmentUserProfile extends Fragment {

    private View mView;

    private RecyclerView rcv_profile;
    private ItemAdapter itemAdapter;


    public FragmentUserProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        rcv_profile = (RecyclerView) mView.findViewById(R.id.rcv_profile);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), RecyclerView.VERTICAL, false);
        rcv_profile.setLayoutManager(linearLayoutManager);

        itemAdapter = new ItemAdapter(mView.getContext());
        itemAdapter.setData(getListItem());

        rcv_profile.setAdapter(itemAdapter);
        return mView;
    }

    private List<Item> getListItem(){
        List<Item> list = new ArrayList<>();

        list.add(new Item(R.drawable.ic_baseline_info_24, "Personal Information"));
        list.add(new Item(R.drawable.ic_baseline_payment_24, "Payment"));
        list.add(new Item(R.drawable.ic_baseline_notifications_24, "Notification"));
        list.add(new Item(R.drawable.ic_baseline_language_24, "Language"));
        list.add(new Item(R.drawable.ic_baseline_help_center_24, "Get Help"));
        list.add(new Item(R.drawable.ic_baseline_feedback_24, "Send us feedback"));

        return list;
    }
}