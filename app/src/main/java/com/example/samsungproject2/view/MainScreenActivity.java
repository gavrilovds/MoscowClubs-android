package com.example.samsungproject2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.ActivityMainScreenBinding;

public class MainScreenActivity extends AppCompatActivity {

    private ActivityMainScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}