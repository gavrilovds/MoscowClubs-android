package com.example.samsungproject2.view.clubs.clubinfo;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.PhotosAdapter;
import com.example.samsungproject2.databinding.FragmentClubPhotosBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;
import com.squareup.picasso.Picasso;


public class ClubPhotosFragment extends Fragment implements PhotosAdapter.OnPhotoListener {

    private FragmentClubPhotosBinding binding;
    private ClubInfoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubPhotosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);
        viewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                binding.photosRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.photosRecyclerView.setAdapter(new PhotosAdapter(club.getImages(), ClubPhotosFragment.this));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onPhotoClick(String url) {
        View photoDialog = getLayoutInflater().inflate(R.layout.photo_dialog, null);
        ImageView imageView = photoDialog.findViewById(R.id.big_image);
        Picasso.get().load(url).into(imageView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(photoDialog);
        builder.show();
    }
}