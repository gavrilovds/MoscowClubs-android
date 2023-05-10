package com.example.samsungproject2.view.clubs.clubinfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.PhotosAdapter;
import com.example.samsungproject2.databinding.AddClubDialogBinding;
import com.example.samsungproject2.databinding.AddClubImageDialogBinding;
import com.example.samsungproject2.databinding.FragmentClubPhotosBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;
import com.squareup.picasso.Picasso;


public class ClubPhotosFragment extends Fragment implements PhotosAdapter.OnPhotoListener {

    private FragmentClubPhotosBinding binding;
    private ClubInfoViewModel viewModel;
    private Dialog addClubImageDialog;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubPhotosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);
        addClubImageDialog = new Dialog(getContext());
        sharedPreferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("USER_TOKEN", null);
        viewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                binding.photosRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.photosRecyclerView.setAdapter(new PhotosAdapter(club.getImages(), ClubPhotosFragment.this));
            }
        });
        if (token != null) {
            if (viewModel.getUserMutableLiveData().getValue().isAdmin() && viewModel.getUserMutableLiveData().getValue().getAdminClubName()
                    .equals(viewModel.getClubMutableLiveData().getValue().getName())) {
                binding.addNewImage.setVisibility(View.VISIBLE);
            }
            binding.addNewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddClubImageDialogBinding binding = AddClubImageDialogBinding.inflate(getLayoutInflater());
                    addClubImageDialog.setContentView(binding.getRoot());
                    addClubImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    addClubImageDialog.show();
                    binding.addImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = binding.imageUrl.getText().toString();
                            if (url.equals(""))
                                Toast.makeText(getContext(), "Введите ссылку", Toast.LENGTH_SHORT).show();
                            else {
                                viewModel.addClubImage(viewModel.getClubMutableLiveData().getValue().getName(),
                                        url);
                                Toast.makeText(getContext(), "Фото добавлено", Toast.LENGTH_LONG);
                                addClubImageDialog.cancel();
                            }
                        }
                    });
                }
            });
        }
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