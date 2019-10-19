package com.example.movieproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.movieproject1.model.FavoriteMovieContract;
import com.example.movieproject1.model.Movie;
//import com.example.movieproject1.model.MovieViewModel;
import com.example.movieproject1.model.Review;
import com.example.movieproject1.model.Trailer;
import com.example.movieproject1.utilities.JSONTool;
import com.example.movieproject1.utilities.NetworkUtils;
import com.example.movieproject1.utilities.ReviewAdapter;
import com.example.movieproject1.utilities.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity
        implements TrailerAdapter.ListItemClickListener {
    private TextView mTitleTextView;
    private ImageView mImageImageView;
    private TextView mSynopsisTextView;
    private TextView mUserRatingTextView;
    private TextView mReleaseDateTextView;
    private ToggleButton mFavoriteMovieTB;
    private boolean isFavorite;
    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewRecyclerView;

    private Movie mMovie;
    private ArrayList<Trailer> mTrailerList;
    private ArrayList<Review> mReviewList;

    private final String BUNDLE_MOVIE = "movie";
    private final String BUNDLE_TRAILER_LIST = "trailer_list";
    private final String BUNDLE_REVIEW_LIST = "review_list";
    private final String INTENT_NAME = "Movie";

    private final String TAG = this.getClass().getSimpleName();


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


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra("Movie")) {
            Log.d(TAG, "the intent has an extra called MOVIE");
            mMovie = intentThatStartedThisActivity.getParcelableExtra(INTENT_NAME);
            mTitleTextView.setText(mMovie.getTitle());
            Picasso.get().load(mMovie.getImage()).into(mImageImageView);
            mSynopsisTextView.setText(mMovie.getSynopsis());
            mUserRatingTextView.setText(mMovie.getUserRating());
            mReleaseDateTextView.setText(mMovie.getReleaseDate());
        }

        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(BUNDLE_MOVIE);
            mTrailerList = savedInstanceState.getParcelableArrayList(BUNDLE_TRAILER_LIST);
            mReviewList = savedInstanceState.getParcelableArrayList(BUNDLE_REVIEW_LIST);
            try {

                mTrailerRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mTrailerRecyclerView.setLayoutManager(layoutManager);
                TrailerAdapter adapter = new TrailerAdapter(mTrailerList, mTrailerList.size(), DetailActivity.this);
                mTrailerRecyclerView.setAdapter(adapter);

                mReviewRecyclerView.setHasFixedSize(true);
                LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
                ReviewAdapter reviewAdapter = new ReviewAdapter(mReviewList, mReviewList.size());
                mReviewRecyclerView.setAdapter(reviewAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }

        Log.d(TAG, "run new TrailerQueryTask().execute(mMovie.getMovieID());");

//        MovieViewModel model = ViewModelProviders.of(this).get(MovieViewModel.class);
//        model.getTrailers().observe(this, trailers -> {
//            TrailerAdapter adapter = new TrailerAdapter(trailers, trailers.size(), DetailActivity.this);
//            mTrailerRecyclerView.setAdapter(adapter);
//        });
//        model.getReviews().observe(this, reviews -> {
//            ReviewAdapter adapter = new ReviewAdapter(reviews, reviews.size());
//            mTrailerRecyclerView.setAdapter(adapter);
//        });

        new TrailerQueryTask().execute(mMovie.getMovieID());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_MOVIE, mMovie);
        outState.putParcelableArrayList(BUNDLE_TRAILER_LIST, mTrailerList);
        outState.putParcelableArrayList(BUNDLE_REVIEW_LIST, mReviewList);
    }

    /**
     * An Async Task to download trailer and review information from URL given
     * and display them in recycler view.
     */
    class TrailerQueryTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            String movieID = params[0];
            URL urlTrailers = NetworkUtils.buildTrailersUrl(movieID);
            Log.d(TAG, "the Trailers URL is : " + urlTrailers.toString());
            URL urlReviews = NetworkUtils.buildReviewsUrl(movieID);
            Log.d(TAG, "the Reviews URL is : " + urlReviews.toString());

            String trailersSearchResults = null;
            String reviewsSearchResults = null;
            try {
                trailersSearchResults = NetworkUtils.getResponseFromHttpUrl(urlTrailers);
                reviewsSearchResults = NetworkUtils.getResponseFromHttpUrl(urlReviews);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mTrailerList = JSONTool.parseTrailerListJson(trailersSearchResults);
            mReviewList = JSONTool.parseReviewListJson(reviewsSearchResults);
            isFavoriteMovie();
            return null;
        }

        /**
         * After the async downloading, this function displays trailers and reviews in
         * two recycler views.
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
//                Log.d(TAG, "the first trailer's name is " + mTrailerList.get(0).getName());
//                Log.d(TAG, "the first review's author is " + mReviewList.get(0).getAuthor());

                mTrailerRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mTrailerRecyclerView.setLayoutManager(layoutManager);
                TrailerAdapter adapter = new TrailerAdapter(mTrailerList, mTrailerList.size(), DetailActivity.this);
                mTrailerRecyclerView.setAdapter(adapter);

                mReviewRecyclerView.setHasFixedSize(true);
                LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
                ReviewAdapter reviewAdapter = new ReviewAdapter(mReviewList, mReviewList.size());
                mReviewRecyclerView.setAdapter(reviewAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mFavoriteMovieTB.setChecked(isFavorite);
            Log.d(TAG, "toggle button is checked? " + String.valueOf(mFavoriteMovieTB.isChecked()));

        }
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
        String key = mTrailerList.get(index).getKey();
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
            Uri uri = addFavorite();
            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            removeFavorite();
        }
    }

    /**
     * Check if a movie is a favorite movie and change isFavorite accordingly
     */
    public void isFavoriteMovie() {
        String selection = FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID + "=" + mMovie.getMovieID();
        Cursor cursor = getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                selection,
                null,
                null);
        int count = cursor.getCount();
        if (count > 0) {
            isFavorite = true;
        } else if (count == 0) {
            isFavorite = false;
        }

    }

    /**
     * Add movie to favorite table in local database
     * @return Uri of insert operation
     */
    public Uri addFavorite() {
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_ID, mMovie.getMovieID());
        contentValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_TITLE, mMovie.getTitle());
        contentValues.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_MOVIE_IMAGE, mMovie.getImage());
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI, contentValues);
        return uri;
    }

    /**
     * Revove movie from favorite table in local database
     * @return true: removed from database, false: failed to remove
     */
    public boolean removeFavorite() {

        String stringId = mMovie.getMovieID();
        Uri uri = FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        int numRemoved = getContentResolver().delete(uri, null, null);

        if (numRemoved > 0) {
            return true;
        } else {
            return false;
        }

    }
}
