package vn.edu.fpt.projectprm392.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.activities.SignInActivity;
import vn.edu.fpt.projectprm392.models.Item;
import vn.edu.fpt.projectprm392.adapters.ItemAdapter;

public class FragmentUserProfile extends Fragment {

    private View mView;
    private RecyclerView rcv_profile;
    private ItemAdapter itemAdapter;
    private TextView tvProfileEmail;
    private CardView cardViewProfile, cardViewAuthenticate;
    private  FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button btnLoginOrRegister,btnSignOut;
    private ProgressBar pgbProfile;

    public FragmentUserProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Get view
        cardViewProfile = mView.findViewById(R.id.cardView_profile);
        cardViewAuthenticate = mView.findViewById(R.id.authenticate_cardView);
        rcv_profile =  mView.findViewById(R.id.rcv_profile);
        tvProfileEmail = mView.findViewById(R.id.tv_profileEmail);
        btnLoginOrRegister = mView.findViewById(R.id.btn_loginOrRegister);
        btnSignOut = mView.findViewById(R.id.btn_signOut);
        pgbProfile = mView.findViewById(R.id.pgb_profile);

        // Check user authentication
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null) {
            cardViewProfile.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.GONE);
            pgbProfile.setVisibility(View.GONE);
            cardViewAuthenticate.setVisibility(View.VISIBLE);
        } else {
            cardViewProfile.setVisibility(View.VISIBLE);
            tvProfileEmail.setText(user.getEmail());
            btnSignOut.setVisibility(View.VISIBLE);
            cardViewAuthenticate.setVisibility(View.GONE);
        }

        // Set up login or register button
        btnLoginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });

        // Set up sign out button
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewProfile.setVisibility(View.INVISIBLE);
                pgbProfile.setVisibility(View.VISIBLE);
                mAuth.signOut();
                cardViewProfile.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.GONE);
                cardViewAuthenticate.setVisibility(View.VISIBLE);
            }
        });

        // Set up recycler view
        rcv_profile.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        itemAdapter = new ItemAdapter(mView.getContext());
        itemAdapter.setData(getListItem());
        rcv_profile.setAdapter(itemAdapter);
        return mView;
    }

    private List<Item> getListItem(){
        List<Item> list = new ArrayList<>();

        list.add(new Item(R.drawable.ic_info, "Personal Information"));
        list.add(new Item(R.drawable.ic_payment, "Payment"));
        list.add(new Item(R.drawable.ic_notifications, "Notification"));
        list.add(new Item(R.drawable.ic_language, "Language"));
        list.add(new Item(R.drawable.ic_help_center, "Get Help"));
        list.add(new Item(R.drawable.ic_feedback, "Send us feedback"));

        return list;
    }
}