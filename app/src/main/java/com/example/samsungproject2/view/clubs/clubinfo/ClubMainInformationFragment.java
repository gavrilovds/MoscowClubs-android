package com.example.samsungproject2.view.clubs.clubinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
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

        binding.webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(binding.webSite.getText().toString()));
                startActivity(intent);
            }
        });

        binding.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + binding.phoneNumber.getText().toString()));
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}