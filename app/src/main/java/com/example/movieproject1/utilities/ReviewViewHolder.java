package com.example.movieproject1.utilities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieproject1.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView mReviewer;
    TextView mReviewContent;
    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        mReviewer = (TextView) itemView.findViewById(R.id.tv_reviewer);
        mReviewContent = (TextView)itemView.findViewById(R.id.tv_review_content);

    }
}
