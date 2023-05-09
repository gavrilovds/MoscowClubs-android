package com.example.samsungproject2.view.clubs.clubinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.ClubInfoPageAdapter;
import com.example.samsungproject2.databinding.FragmentClubInfoBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;


public class ClubInfoFragment extends Fragment {

    private FragmentClubInfoBinding binding;
    final static String CLUB_NAME_KEY = "CLUB_NAME";
    final static String FROM = "FROM";
    private ClubInfoViewModel clubInfoViewModel;
    private ViewPager2 viewPager;
    private ClubInfoPageAdapter clubInfoPageAdapter;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubInfoBinding.inflate(inflater, container, false);
        clubInfoViewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);

        preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        String token = preferences.getString("USER_TOKEN", null);
        if (token != null) {
            binding.favouriteButton.setVisibility(View.VISIBLE);
            clubInfoViewModel.getUser(token);
            clubInfoViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user.getFavouriteClubs().stream().anyMatch(club -> club.getName().equals(getArguments().getString(CLUB_NAME_KEY)))){
                        binding.favouriteButton.setBackgroundResource(R.drawable.favourite_for_club_info_red);
                    }
                    else{
                        binding.favouriteButton.setBackgroundResource(R.drawable.favourite_for_club_info_grey);
                    }
                }
            });
            binding.favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = clubInfoViewModel.getUserMutableLiveData().getValue();
                    if (user.getFavouriteClubs() != null && user.getFavouriteClubs().stream().anyMatch(club -> club.getName().equals(getArguments().getString(CLUB_NAME_KEY)))){
                        clubInfoViewModel.deleteClubFromFavourites(token, getArguments().getString(CLUB_NAME_KEY));
                    }
                    else{
                        clubInfoViewModel.addClubToFavourites(token, getArguments().getString(CLUB_NAME_KEY));
                    }
                    binding.favouriteButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.add_to_favourite_anim));
                }
            });
        }
        clubInfoViewModel.getClub(getArguments().getString(CLUB_NAME_KEY));
        clubInfoViewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                binding.clubName.setText(club.getName());
                Picasso.get().load(club.getImages().get(0).getUrl()).into(binding.clubPreviewImg);
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getString(FROM).equals("map_fragment"))
                    Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_clubInfoFragment_to_bottom_nav_map);
                else if (getArguments().getString(FROM).equals("user_profile_fragment"))
                    Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_clubInfoFragment_to_bottom_nav_user_profile);
                else
                    Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(R.id.action_clubInfoFragment_to_bottom_nav_clubs);
            }
        });
        clubInfoPageAdapter = new ClubInfoPageAdapter(this);
        viewPager = binding.viewpager;
        viewPager.setAdapter(clubInfoPageAdapter);
        new TabLayoutMediator(binding.slidingTabs, viewPager,
                ((tab, position) -> tab.setText(clubInfoPageAdapter.getTabTitles()[position]))).attach();
        return binding.getRoot();
    }
}