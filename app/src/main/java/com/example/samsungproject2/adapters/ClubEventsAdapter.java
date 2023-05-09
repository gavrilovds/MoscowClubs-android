package com.example.samsungproject2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.databinding.EventsListDesignBinding;
import com.example.samsungproject2.model.Event;
import com.example.samsungproject2.utils.MonthUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClubEventsAdapter extends RecyclerView.Adapter<ClubEventsAdapter.ClubEventsViewHolder> {

    private List<Event> data;
    private OnEventListener onEventListener;


    public ClubEventsAdapter(List<Event> data, OnEventListener onEventListener) {
        this.data = data;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public ClubEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventsListDesignBinding binding = EventsListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ClubEventsViewHolder(binding.getRoot(), onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubEventsViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getImageUrl()).into(holder.binding.eventImage);
        holder.binding.eventName.setText(data.get(position).getEventName());
        String[] dates = dateHelper(data.get(position).getDate());
        holder.binding.day.setText(dates[0]);
        holder.binding.month.setText(dates[1]);
        holder.binding.time.setText(dates[2]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClubEventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public EventsListDesignBinding binding;
        OnEventListener onEventListener;

        public ClubEventsViewHolder(@NonNull View itemView, OnEventListener onEventListener) {
            super(itemView);
            this.onEventListener = onEventListener;
            itemView.setOnClickListener(this);
            binding = EventsListDesignBinding.bind(itemView);
        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(data.get(getAdapterPosition()).getUrl());
        }
    }

    public interface OnEventListener{
        void onEventClick(String url);
    }

    private String[] dateHelper(String date) {
        String[] parts = date.split("[- ]");
        String[] result = new String[3];
        result[0] = parts[0]; // число месяца
        result[1] = parts[1]; // номер месяца
        result[2] = parts[3]; // время
        result[1] = MonthUtils.months.get(result[1]);
        return result;
    }

}
