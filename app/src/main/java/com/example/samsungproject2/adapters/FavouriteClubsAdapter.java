package com.example.samsungproject2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.CommentListDesignBinding;
import com.example.samsungproject2.databinding.FavouriteClubsListDesignBinding;
import com.example.samsungproject2.model.Club;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteClubsAdapter extends RecyclerView.Adapter<FavouriteClubsAdapter.FavouriteClubsViewHolder> {

    private List<Club> data;
    private OnFavouriteClubsListener onFavouriteClubsListener;

    public FavouriteClubsAdapter(List<Club> data, OnFavouriteClubsListener onFavouriteClubsListener) {
        this.data = data;
        this.onFavouriteClubsListener = onFavouriteClubsListener;
    }

    @NonNull
    @Override
    public FavouriteClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavouriteClubsListDesignBinding binding = FavouriteClubsListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new FavouriteClubsViewHolder(binding.getRoot(), onFavouriteClubsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteClubsViewHolder holder, int position) {
        holder.binding.clubNameText.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getImages().get(0).getUrl()).into(holder.binding.clubImage);
        holder.binding.favouriteClubsItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.favourite_clubs_list_anim));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FavouriteClubsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public FavouriteClubsListDesignBinding binding;
        OnFavouriteClubsListener onFavouriteClubsListener;
        public FavouriteClubsViewHolder(@NonNull View itemView, OnFavouriteClubsListener onFavouriteClubsListener) {
            super(itemView);
            this.onFavouriteClubsListener = onFavouriteClubsListener;
            itemView.setOnClickListener(this);
            binding = FavouriteClubsListDesignBinding.bind(itemView);
        }

        @Override
        public void onClick(View v) {
            onFavouriteClubsListener.onFavouriteClubsClick(data.get(getAdapterPosition()).getName());
        }
    }

    public interface OnFavouriteClubsListener{
        void onFavouriteClubsClick(String clubName);
    }

}
