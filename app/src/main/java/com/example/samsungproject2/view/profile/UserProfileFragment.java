package com.example.samsungproject2.view.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.FavouriteClubsAdapter;
import com.example.samsungproject2.adapters.MyCommentsAdapter;
import com.example.samsungproject2.databinding.FragmentUserProfileBinding;
import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.viewmodel.profile.UserProfileViewModel;


public class UserProfileFragment extends Fragment implements FavouriteClubsAdapter.OnFavouriteClubsListener, MyCommentsAdapter.OnCommentListener {

    private FragmentUserProfileBinding binding;
    private UserProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        if (preferences.getString("USER_TOKEN", null) == null)
            NavHostFragment.findNavController(this).navigate(R.id.action_bottom_nav_user_profile_to_bottom_nav_profile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(UserProfileViewModel.class);
        SharedPreferences preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        String token = preferences.getString("USER_TOKEN", null);
        if (token != null) {
            viewModel.getUser(preferences.getString("USER_TOKEN", null));
            viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    binding.username.setText(user.getName());
                    binding.favouriteClubsRecyclerView.setAdapter(new FavouriteClubsAdapter(user.getFavouriteClubs(), UserProfileFragment.this));
                    binding.favouriteClubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    binding.myCommentsRecyclerView.setAdapter(new MyCommentsAdapter(user.getComments(), UserProfileFragment.this));
                    binding.myCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                }
            });
        }
        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.settingsButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.settings_anim));
                Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                        .navigate(R.id.action_bottom_nav_user_profile_to_profileSettingsFragment);
            }
        });
        binding.favouriteClubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.favouriteClubsButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_press_anim));
                binding.myCommentsRecyclerView.setVisibility(View.GONE);
                binding.noComments.setVisibility(View.GONE);
                if (viewModel.getMutableLiveData().getValue().getFavouriteClubs().isEmpty())
                    binding.noFavouriteClubs.setVisibility(View.VISIBLE);
                else
                    binding.favouriteClubsRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.myCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.myCommentsButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.button_press_anim));
                binding.favouriteClubsRecyclerView.setVisibility(View.GONE);
                binding.noFavouriteClubs.setVisibility(View.GONE);
                if (viewModel.getMutableLiveData().getValue().getComments().isEmpty())
                    binding.noComments.setVisibility(View.VISIBLE);
                else
                    binding.myCommentsRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment exitDialog = new ExitAccountDialog();
                exitDialog.show(getActivity().getSupportFragmentManager(), "exit");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onFavouriteClubsClick(String clubName) {
        Bundle bundle = new Bundle();
        bundle.putString("CLUB_NAME", clubName);
        bundle.putString("FROM", "user_profile_fragment");
        Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                .navigate(R.id.action_bottom_nav_user_profile_to_clubInfoFragment, bundle);
    }

    @Override
    public void onCommentClick(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("COMMENT_ID", id);
        DialogFragment deleteComment = new DeleteCommentDialog();
        deleteComment.setArguments(bundle);
        deleteComment.show(getActivity().getSupportFragmentManager(), "deleteComment");
    }
}