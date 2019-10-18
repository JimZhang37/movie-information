package com.example.movieproject1.utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieproject1.R;
import com.example.movieproject1.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private ArrayList<Review> mReviewList;
    private int mItemCount;
    private int mNumberOfViewHolder;
    private final String TAG = this.getClass().getSimpleName();

    public ReviewAdapter(ArrayList<Review> mReviewList, int mItemCount) {
        this.mReviewList = mReviewList;
        this.mItemCount = mItemCount;
        mNumberOfViewHolder = 0;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_viewholder;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        mNumberOfViewHolder++;
        Log.d(TAG, "a New Review ViewHolder is created. Current number is: " + String.valueOf(mNumberOfViewHolder));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.mReviewer.setText(mReviewList.get(position).getAuthor());
        holder.mReviewContent.setText(mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
}
