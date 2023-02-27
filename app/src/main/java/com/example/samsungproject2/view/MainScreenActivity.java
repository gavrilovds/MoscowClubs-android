package com.example.samsungproject2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.ActivityMainScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreenActivity extends AppCompatActivity {

    private ActivityMainScreenBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        bottomNavigationView = binding.bottomNavBar;

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MapFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_map);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.bottom_nav_map:
                    fragment = new MapFragment();
                    break;
                case R.id.bottom_nav_clubs:
                    fragment = new ClubsFragment();
                    break;
                case R.id.bottom_nav_profile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

            return true;
        });
    }
}