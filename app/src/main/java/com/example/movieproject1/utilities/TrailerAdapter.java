package com.example.movieproject1.utilities;

import com.example.movieproject1.R;
import com.example.movieproject1.model.Trailer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private int mNumberOfViewHolder;
    private int mSize;
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Trailer> mTrailerList;
    private ListItemClickListener mListener;

    public interface ListItemClickListener {
        void onListItemClick(int index);
    }

    public TrailerAdapter(ArrayList<Trailer> trailerList, int size, ListItemClickListener listItemClickListener) {
        mNumberOfViewHolder = 0;
        mTrailerList = trailerList;
        mSize = size;
        mListener = listItemClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_viewholder;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        mNumberOfViewHolder++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.mTrailer.setText(mTrailerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    /**
     * ViewHolder for Trailer has only one textview for trailer information
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder
            implements android.view.View.OnClickListener {

        private TextView mTrailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailer = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onListItemClick(position);
        }
    }
}
