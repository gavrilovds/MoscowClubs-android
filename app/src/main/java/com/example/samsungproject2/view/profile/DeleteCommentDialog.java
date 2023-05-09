package com.example.samsungproject2.view.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.samsungproject2.viewmodel.profile.UserProfileViewModel;

public class DeleteCommentDialog extends DialogFragment {
    private UserProfileViewModel viewModel;
    private SharedPreferences preferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(UserProfileViewModel.class);
        preferences = getActivity().getSharedPreferences("MoscowClubs", Context.MODE_PRIVATE);
        Long id = getArguments().getLong("COMMENT_ID");
        builder.setMessage("Вы уверены, что хотите удалить комментарий?")
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteComment(id, preferences.getString("USER_TOKEN", null));
                        Toast.makeText(getContext(), "Комментарий удалён", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
