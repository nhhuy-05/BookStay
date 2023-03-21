package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.databinding.ActivityMainBinding;
import vn.edu.fpt.projectprm392.fragments.FragmentBookings;
import vn.edu.fpt.projectprm392.fragments.FragmentHome;
import vn.edu.fpt.projectprm392.fragments.FragmentSavedPlace;
import vn.edu.fpt.projectprm392.fragments.FragmentUserProfile;
import vn.edu.fpt.projectprm392.models.District;
import vn.edu.fpt.projectprm392.models.Hotel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mViewBinding;

    private BottomNavigationView bottomNavigation;
    public static MainActivity instance;

    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    private void bindingView() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void bindingAction() {
        bottomNavigation.setOnItemSelectedListener(this::onItemNavigationClick);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onItemNavigationClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                replaceFragment(new FragmentHome());
                return true;
            case R.id.nav_search:
                replaceFragment(new FragmentSavedPlace());
                return true;
            case R.id.nav_add:
                replaceFragment(new FragmentBookings());
                return true;
            case R.id.nav_user:
                replaceFragment(new FragmentUserProfile());
                return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);
        replaceFragment(new FragmentHome());
        bindingView();
        bindingAction();
    }


}