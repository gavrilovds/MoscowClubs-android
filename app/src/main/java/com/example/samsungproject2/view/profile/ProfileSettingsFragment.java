package com.example.samsungproject2.view.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.AddClubDialogBinding;
import com.example.samsungproject2.databinding.ChangeNameDialogBinding;
import com.example.samsungproject2.databinding.FragmentProfileSettingsBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.ClubImages;
import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.viewmodel.profile.ProfileSettingsViewModel;

import java.util.ArrayList;
import java.util.List;


public class ProfileSettingsFragment extends Fragment {

    private FragmentProfileSettingsBinding binding;
    private ProfileSettingsViewModel viewModel;
    private SharedPreferences preferences;
    private Dialog addClubDialog;
    private Dialog changeNameDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileSettingsViewModel.class);
        preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        addClubDialog = new Dialog(getContext());
        changeNameDialog = new Dialog(getContext());
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                        .navigate(R.id.action_profileSettingsFragment_to_bottom_nav_user_profile);
            }
        });
        String token = preferences.getString("USER_TOKEN", null);
        if (token != null) {
            viewModel.getUser(token);
            viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user.isAdmin() && user.getAdminClubName().equals(""))
                        binding.addClub.setVisibility(View.VISIBLE);
                }
            });
        }

        binding.addClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClubDialogBinding binding = AddClubDialogBinding.inflate(LayoutInflater.from(getContext()));
                addClubDialog.setContentView(binding.getRoot());
                addClubDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addClubDialog.show();
                binding.addClubButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Club newClub = checkUpForm(binding);
                        viewModel.addClub(newClub);
                        viewModel.setAdmin(token, newClub.getName());
                        addClubDialog.cancel();
                    }
                });
            }
        });

        binding.changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNameDialogBinding binding = ChangeNameDialogBinding.inflate(LayoutInflater.from(getContext()));
                changeNameDialog.setContentView(binding.getRoot());
                changeNameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                changeNameDialog.show();
                binding.changeNameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = binding.newName.getText().toString();
                        if (newName.length() < 3)
                            Toast.makeText(getContext(), "Имя слишком короткое", Toast.LENGTH_LONG).show();
                        else{
                            viewModel.changeName(token, newName);
                            Toast.makeText(getContext(), "Имя изменено", Toast.LENGTH_SHORT).show();
                            changeNameDialog.cancel();
                        }
                    }
                });
            }
        });


        return binding.getRoot();
    }

    private Club checkUpForm(AddClubDialogBinding binding) {
        Club newClub = new Club();
        newClub.setName(binding.clubName.getText().toString());
        newClub.setDescription(binding.clubDescription.getText().toString());
        newClub.setAddress(binding.clubAddress.getText().toString());
        newClub.setLatitude(Double.valueOf(binding.latitude.getText().toString()));
        newClub.setLongitude(Double.valueOf(binding.longitude.getText().toString()));
        if (!binding.clubPreviewImg.getText().toString().equals("")) {
            List<ClubImages> clubImages = new ArrayList<>();
            ClubImages image = new ClubImages();
            image.setUrl(binding.clubPreviewImg.getText().toString());
            clubImages.add(image);
            newClub.setImages(clubImages);
        }
        newClub.setClosestUnderground(binding.closestUnderground.getText().toString());
        newClub.setMeanPrice(Long.valueOf(binding.medianPrice.getText().toString()));
        newClub.setPeopleAmount(Long.valueOf(binding.peopleAmount.getText().toString()));
        newClub.setWebSite(binding.webSite.getText().toString());
        newClub.setPhoneNumber(binding.phoneNumber.getText().toString());
        newClub.setWorkTime(binding.workTime.getText().toString());
        return newClub;
    }


}