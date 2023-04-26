package com.example.samsungproject2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.databinding.PhotosListDesignBinding;
import com.example.samsungproject2.model.ClubImages;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<ClubImages> data;
    private OnPhotoListener onPhotoListener;

    public PhotosAdapter(List<ClubImages> data, OnPhotoListener onPhotoListener) {
        this.data = data;
        this.onPhotoListener = onPhotoListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhotosListDesignBinding binding = PhotosListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PhotoViewHolder(binding.getRoot(), onPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Picasso.get().load(data.get(position).getUrl()).into(holder.binding.firstImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public PhotosListDesignBinding binding;
        public OnPhotoListener onPhotoListener;
        public PhotoViewHolder(@NonNull View itemView, OnPhotoListener onPhotoListener) {
            super(itemView);
            this.onPhotoListener = onPhotoListener;
            itemView.setOnClickListener(this);
            binding = PhotosListDesignBinding.bind(itemView);
        }

        @Override
        public void onClick(View v) {
            onPhotoListener.onPhotoClick(data.get(getAdapterPosition()).getUrl());
        }
    }

    public interface OnPhotoListener{
        void onPhotoClick(String url);
    }

}
