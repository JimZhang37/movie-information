package com.example.movieproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.ToggleButton;

import com.example.movieproject1.model.FavoriteMovieContract;
import com.example.movieproject1.model.Movie;

import com.example.movieproject1.utilities.DetailViewModel;
import com.example.movieproject1.utilities.ReviewAdapter;
import com.example.movieproject1.utilities.TrailerAdapter;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity
        implements TrailerAdapter.ListItemClickListener {
    private TextView mTitleTextView;
    private ImageView mImageImageView;
    private TextView mSynopsisTextView;
    private TextView mUserRatingTextView;
    private TextView mReleaseDateTextView;
    private ToggleButton mFavoriteMovieTB;
    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;

    private Movie mMovie;

    private final String BUNDLE_MOVIE = "movie";
    private final String INTENT_NAME = "Movie";

    private final String TAG = this.getClass().getSimpleName();

    private DetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mImageImageView = (ImageView) findViewById(R.id.iv_movie_poster_detail);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
        mUserRatingTextView = (TextView) findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_data);
        mTrailerRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);
        mReviewRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        mFavoriteMovieTB = (ToggleButton) findViewById(R.id.tb_favorite);
/*TODO(H)
        Hey there! Based on the code of this app, I can guess that you are a really advanced student. To learn more, one thing you could check is a package called “ButterKnife”. “Butterknife” is a light weight library that you could use to inject views into Android components in an easier way, which, can make your life as a developer much easier.

        For example, you could inject views using “ButterKnife” like this:

        @BindView(R.id.step_number)
        TextView stepNumber;
        @BindView(R.id.short_description)
        TextView shortDescicassoription;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.tutorial_button)
        TextView tutorialButton;
        More to read:
        http://jakewharton.github.io/butterknife/
        https://code.tutsplus.com/tutorials/quick-tip-using-butter-knife-to-inject-views-on-android--cms-23542*/



        mTrailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mTrailerRecyclerView.setLayoutManager(layoutManager);

        model = ViewModelProviders.of(this).get(DetailViewModel.class);
        model.getTrailerData().observe(this, data -> {
            TrailerAdapter adapter = new TrailerAdapter(data, data.size(), DetailActivity.this);
            mTrailerRecyclerView.setAdapter(adapter);

        });

        mReviewRecyclerView.setHasFixedSize(true);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
        mReviewRecyclerView.setLayoutManager(reviewLayoutManager);

        model.getReviewData().observe(this, data -> {
            ReviewAdapter adapter = new ReviewAdapter(data, data.size());
            mReviewRecyclerView.setAdapter(adapter);

        });

        model.getIsFavorate().observe(this, data ->{
            mFavoriteMovieTB.setChecked(data);
        });


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra("Movie")) {
            Log.d(TAG, "the intent has an extra called MOVIE");
            mMovie = intentThatStartedThisActivity.getParcelableExtra(INTENT_NAME);


            mTitleTextView.setText(mMovie.getTitle());
            Picasso.get().load(mMovie.getImage()).into(mImageImageView);
            mSynopsisTextView.setText(mMovie.getSynopsis());
            mUserRatingTextView.setText(mMovie.getUserRating());
            mReleaseDateTextView.setText(mMovie.getReleaseDate());

            model.setData(mMovie);
        }

        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(BUNDLE_MOVIE);
//            model.setData(mMovie);


        }

        Log.d(TAG, "run new TrailerQueryTask().execute(mMovie.getMovieID());");

        return;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_MOVIE, mMovie);

    }

    /**
     * A function from interface: TrailerAdapter.ListItemClickListener
     * When the trailer link is clicked, the app chooses to display the trailer in
     * youtube app or in a web.
     *
     * @param index the id of trailer
     */
    @Override
    public void onListItemClick(int index) {
        String key = model.getTrailerData().getValue().get(index).getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    /**
     * change the status of favorite movie when the toggle button is clicked
     *
     * @param view
     */
    public void onClickFavoriteMovieToggleButton(View view) {

        if (mFavoriteMovieTB.isChecked()) {
            model.addFavorite(mMovie.getMovieID());

//            Uri uri = addFavorite();
//            if (uri != null) {
//                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//            }
        } else {
            model.removeFavorite(mMovie.getMovieID());
//            removeFavorite();
        }
    }

}
