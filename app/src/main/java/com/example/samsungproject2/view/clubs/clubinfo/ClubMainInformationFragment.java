package com.example.samsungproject2.view.clubs.clubinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samsungproject2.databinding.FragmentClubMainInformationFragmentBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;


public class ClubMainInformationFragment extends Fragment {

    private FragmentClubMainInformationFragmentBinding binding;
    private ClubInfoViewModel clubInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubMainInformationFragmentBinding.inflate(inflater, container, false);
        clubInfoViewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);
        clubInfoViewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                binding.clubAddress.setText(club.getAddress());
                binding.closestUnderground.setText(club.getClosestUnderground());
                binding.medianPrice.setText(String.valueOf(club.getMeanPrice()));
                binding.peopleAmount.setText(String.valueOf(club.getPeopleAmount()));
                binding.workTime.setText(club.getWorkTime());
                binding.phoneNumber.setText(club.getPhoneNumber());
                binding.webSite.setText(club.getWebSite());
            }
        });
        return binding.getRoot();
    }
}