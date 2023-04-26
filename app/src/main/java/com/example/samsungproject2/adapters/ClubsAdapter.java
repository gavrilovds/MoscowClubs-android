package com.example.samsungproject2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.ClubListDesignBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.savers.ImageSaver;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubViewHolder> {

    private List<Club> data;
    private OnClubListener onClubListener;

    public ClubsAdapter(List<Club> data, OnClubListener onClubListener) {
        this.data = data;
        this.onClubListener = onClubListener;
    }

    public void updateData(List<Club> newData) {
        data = newData;
    }

    @NonNull
    @Override
    public ClubsAdapter.ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClubListDesignBinding binding = ClubListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ClubViewHolder(binding.getRoot(), onClubListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsAdapter.ClubViewHolder holder, int position) {
        holder.binding.clubName.setText(data.get(position).getName());
        holder.binding.clubDescription.setText(data.get(position).getDescription());
        Picasso.get().load(data.get(position).getImages().get(0).getUrl()).into(holder.binding.clubPreviewImg);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ClubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ClubListDesignBinding binding;
        OnClubListener onClubListener;

        public ClubViewHolder(@NonNull View itemView, OnClubListener onClubListener) {
            super(itemView);
            this.onClubListener = onClubListener;
            itemView.setOnClickListener(this);
            binding = ClubListDesignBinding.bind(itemView);
        }

        @Override
        public void onClick(View v) {
            onClubListener.onClubClick(data.get(getAdapterPosition()).getName());
        }
    }

    public interface OnClubListener {
        void onClubClick(String clubName);
    }


}
