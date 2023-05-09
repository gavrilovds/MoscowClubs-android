package com.example.samsungproject2.view.clubs.clubinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.CommentsAdapter;
import com.example.samsungproject2.databinding.FragmentClubCommentsBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.network.CommentAPI;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;
import com.google.android.material.button.MaterialButton;


public class ClubCommentsFragment extends Fragment {
    private FragmentClubCommentsBinding binding;
    private ClubInfoViewModel clubInfoViewModel;
    private SharedPreferences preferences;
    private Dialog addCommentDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubCommentsBinding.inflate(inflater, container, false);
        clubInfoViewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);
        preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        String token = preferences.getString("USER_TOKEN", null);
        addCommentDialog = new Dialog(getContext());
        if (token != null){
            binding.addCommentButton.setVisibility(View.VISIBLE);
            binding.addCommentText.setText("Добавьте свой комментарий об этом месте");
        }
        clubInfoViewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                Log.d("help", "helpe");
                binding.commentsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.commentsRecyclerview.setAdapter(new CommentsAdapter(club.getComments()));
            }
        });
        binding.addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText commentEditText;
                MaterialButton addComment;
                addCommentDialog.setContentView(R.layout.add_comment_dialog);
                addCommentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addComment = addCommentDialog.findViewById(R.id.add_comment);
                commentEditText = addCommentDialog.findViewById(R.id.comment_text);
                addComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clubInfoViewModel.addComment(commentEditText.getText().toString(), token,
                                clubInfoViewModel.getClubMutableLiveData().getValue().getName());
                        addCommentDialog.cancel();
                    }
                });
                addCommentDialog.show();
            }
        });
        return binding.getRoot();
    }
}