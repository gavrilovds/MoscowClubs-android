package com.example.samsungproject2.view.clubs.clubinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.FragmentClubEventsBinding;


public class ClubEventsFragment extends Fragment {

    private FragmentClubEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}