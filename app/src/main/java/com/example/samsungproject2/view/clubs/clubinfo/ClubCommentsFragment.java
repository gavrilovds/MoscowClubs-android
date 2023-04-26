package com.example.samsungproject2.view.clubs.clubinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samsungproject2.adapters.CommentsAdapter;
import com.example.samsungproject2.databinding.FragmentClubCommentsBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;


public class ClubCommentsFragment extends Fragment {

    private FragmentClubCommentsBinding binding;
    private ClubInfoViewModel clubInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubCommentsBinding.inflate(inflater, container, false);
        clubInfoViewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);

        clubInfoViewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                Log.d("help", "helpe");
                binding.commentsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.commentsRecyclerview.setAdapter(new CommentsAdapter(club.getComments()));
            }
        });
        return binding.getRoot();
    }
}