package com.example.samsungproject2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.MyCommentsListDesignBinding;
import com.example.samsungproject2.model.Comment;

import java.util.List;

public class MyCommentsAdapter extends RecyclerView.Adapter<MyCommentsAdapter.MyCommentsViewHolder> {

    private List<Comment> data;
    private OnCommentListener onCommentListener;

    public MyCommentsAdapter(List<Comment> data, OnCommentListener onCommentListener) {
        this.data = data;
        this.onCommentListener = onCommentListener;
    }

    @NonNull
    @Override
    public MyCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyCommentsListDesignBinding binding = MyCommentsListDesignBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyCommentsViewHolder(binding.getRoot(), onCommentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCommentsViewHolder holder, int position) {
        holder.binding.clubNameText.setText(data.get(position).getClubName());
        holder.binding.myCommentText.setText(data.get(position).getText());
        holder.binding.myCommentsItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.favourite_clubs_list_anim));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyCommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MyCommentsListDesignBinding binding;
        OnCommentListener onCommentListener;
        public MyCommentsViewHolder(@NonNull View itemView, OnCommentListener onCommentListener) {
            super(itemView);
            this.onCommentListener = onCommentListener;
            binding = MyCommentsListDesignBinding.bind(itemView);
            binding.deleteCommentButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCommentListener.onCommentClick(data.get(getAdapterPosition()).getId());
        }
    }

    public interface OnCommentListener{
        void onCommentClick(Long id);
    }

}
