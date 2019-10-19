package com.example.movieproject1.utilities;

import com.example.movieproject1.*;
import com.example.movieproject1.model.Movie;
import com.squareup.picasso.Picasso;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private static final String TAG = ImageAdapter.class.getSimpleName();
    private int mNumberOfItem;
    private ArrayList<Movie> mMovieList;
    private Context mContext;
    private ListItemClickListener mListItemClickListener;
    private int mNumberOfViewHolder;
    //public ImageAdapter(int numberOfItem){
    //    mNumberOfItem = numberOfItem;
    //}

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ImageAdapter(Context context, int numberOfItem, ArrayList<Movie> movieList, ListItemClickListener listItemClickListener) {
        mContext = context;
        mNumberOfItem = numberOfItem;
        mMovieList = movieList;
        mListItemClickListener = listItemClickListener;
        mNumberOfViewHolder = 0;
    }

    public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.image_viewholder;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        mNumberOfViewHolder++;
        Log.d(TAG, "a New ViewHolder is created. Current number is: " + String.valueOf(mNumberOfViewHolder));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        Log.d(TAG, "The position of the current data is " + position);

//        holder.mText.setText(mMovieList.getMovieTitleByPosition(position));
/*Completed (D) String url = mMovieList.getMovieImageByPosition(position);
        Picasso.get().load(url).into(holder.mMoviePoster);
        To learn more, here, you can also try to use error() and placeholder() provided by Picasso to avoid potential crash due to empty or null image url values. Before the error placeholder is shown, Picasso will retry your request for three times.

        You could try to use these two methods as shown in the sample code below (from Picasso documentation):

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(imageView);
        Since the quality of the data maintained in this movie database is really good, using Picasso without error() might not cause any problem. However, when it comes to some other APIs (unfortunately, Spotify is one of them), the chance of fighting against some strange values could get higher. So that's why I strongly suggest you to use these two methods in your future projects.

        Try it yourself! :)*/

        Picasso.get().load(mMovieList.get(position).getImage()).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mNumberOfItem;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {
        private ImageView mMoviePoster;
        private final String TAG = this.getClass().getSimpleName();

        public ImageViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
//            mText = (TextView) itemView.findViewById(R.id.tv_text);//import R from movieproject 1
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();

            Log.d(TAG, "onClick is called");
//Completed (E)  I have a suggestion here. In the future if you are going to deploy your production Android app, you can consider to remove these Log messages since they are only for developers' use and your users won't see them. By logging out the data in your users' phones, it can potentially put a burden on the memory of their devices. You can find details about this suggestion here in Android's Doc (Turn off logging and debugging section):
            mListItemClickListener.onListItemClick(clickPosition);
        }
    }

}
