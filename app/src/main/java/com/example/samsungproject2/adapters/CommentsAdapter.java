package com.example.samsungproject2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.ClubListDesignBinding;
import com.example.samsungproject2.databinding.CommentListDesignBinding;
import com.example.samsungproject2.model.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comment> data;

    public CommentsAdapter(List<Comment> comments) {
        this.data = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentListDesignBinding binding = CommentListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CommentViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.commentText.setText(data.get(position).getText());
        holder.binding.username.setText(data.get(position).getOwner());
        holder.binding.commentItem.startAnimation(AnimationUtils.loadAnimation(holder.binding.commentText.getContext(), R.anim.club_comments_anim));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        public CommentListDesignBinding binding;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommentListDesignBinding.bind(itemView);
        }
    }

}
