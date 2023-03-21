package vn.edu.fpt.projectprm392.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

import javax.annotation.Nullable;

import vn.edu.fpt.projectprm392.R;

public class FragmentLanguage extends Fragment {

    private Button btnEnglish, btnVietnamese,btnBackToFragmentUserProfile;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        // Initialize the buttons
        btnEnglish = view.findViewById(R.id.btn_lang_eng);
        btnVietnamese = view.findViewById(R.id.btn_lang_vi);

        // Set click listeners for the buttons
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the locale to English
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.locale = locale;
                getResources().updateConfiguration(configuration, null);

                // Restart the Activity to apply the new language
                getActivity().recreate();
            }
        });

        btnVietnamese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the locale to Vietnamese
                Locale locale = new Locale("vi");
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.locale = locale;
                getResources().updateConfiguration(configuration, null);

                // Restart the Activity to apply the new language
                getActivity().recreate();
            }
        });

        // Back to FragmentUserProfile
        btnBackToFragmentUserProfile = view.findViewById(R.id.btn_backToFragmentUserProfile);
        btnBackToFragmentUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUserProfile fragmentUserProfile = new FragmentUserProfile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragmentUserProfile).commit();
            }
        });
        return view;
    }
}

