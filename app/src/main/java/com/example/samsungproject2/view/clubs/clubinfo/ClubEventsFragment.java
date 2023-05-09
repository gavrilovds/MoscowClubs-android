package com.example.samsungproject2.view.clubs.clubinfo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungproject2.R;
import com.example.samsungproject2.adapters.ClubEventsAdapter;
import com.example.samsungproject2.databinding.AddClubEventDialogBinding;
import com.example.samsungproject2.databinding.FragmentClubEventsBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.Event;
import com.example.samsungproject2.viewmodel.clubs.ClubInfoViewModel;


public class ClubEventsFragment extends Fragment implements ClubEventsAdapter.OnEventListener {

    private FragmentClubEventsBinding binding;
    private ClubInfoViewModel viewModel;
    private Dialog addClubEventDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClubEventsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ClubInfoViewModel.class);
        addClubEventDialog = new Dialog(getContext());
        viewModel.getClubMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                binding.eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.eventRecyclerView.setAdapter(new ClubEventsAdapter(club.getEvents(), ClubEventsFragment.this));
            }
        });
        if (viewModel.getUserMutableLiveData().getValue().isAdmin() && viewModel.getUserMutableLiveData().getValue().getAdminClubName()
                .equals(viewModel.getClubMutableLiveData().getValue().getName())) {
            binding.addNewEvent.setVisibility(View.VISIBLE);
        }
        binding.addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClubEventDialogBinding binding = AddClubEventDialogBinding.inflate(getLayoutInflater());
                addClubEventDialog.setContentView(binding.getRoot());
                addClubEventDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addClubEventDialog.show();
                binding.addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event event = checkUpForm(binding);
                        event.setClubName(viewModel.getClubMutableLiveData().getValue().getName());
                        viewModel.addEvent(viewModel.getClubMutableLiveData().getValue().getName(), event);
                        Toast.makeText(getContext(), "Событие добавлено", Toast.LENGTH_LONG).show();
                        addClubEventDialog.cancel();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private Event checkUpForm(AddClubEventDialogBinding binding){
        Event event = new Event();
        event.setEventName(binding.eventName.getText().toString());
        event.setImageUrl(binding.eventImage.getText().toString());
        event.setUrl(binding.ticketsUrl.getText().toString());
        event.setDate(binding.eventTime.getText().toString());
        return event;
    }

    @Override
    public void onEventClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}