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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
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
import java.util.Locale;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubViewHolder> implements Filterable {

    private final List<Club> data;
    private List<Club> filteredData;
    private final OnClubListener onClubListener;

    public ClubsAdapter(List<Club> data, OnClubListener onClubListener) {
        this.data = data;
        this.filteredData = data;
        this.onClubListener = onClubListener;
    }


    @NonNull
    @Override
    public ClubsAdapter.ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClubListDesignBinding binding = ClubListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ClubViewHolder(binding.getRoot(), onClubListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsAdapter.ClubViewHolder holder, int position) {
        holder.binding.clubName.setText(filteredData.get(position).getName());
        holder.binding.clubDescription.setText(filteredData.get(position).getDescription());
        if (!filteredData.get(position).getImages().isEmpty())
            Picasso.get().load(filteredData.get(position).getImages().get(0).getUrl()).into(holder.binding.clubPreviewImg);
        else
            Picasso.get().load(R.drawable.empty_image).into(holder.binding.clubPreviewImg);
        holder.binding.clubsItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.club_list_anim));
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty())
                    filteredData = data;
                else {
                    List<Club> filteredList = new ArrayList<>();
                    for (Club club : data) {
                        if (club.getName().toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)))
                            filteredList.add(club);
                    }
                    filteredData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (List<Club>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
