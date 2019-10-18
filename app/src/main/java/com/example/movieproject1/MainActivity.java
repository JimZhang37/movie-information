package com.example.movieproject1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import com.example.movieproject1.model.FavoriteMovieContract;
import com.example.movieproject1.model.Movie;
import com.example.movieproject1.utilities.ImageAdapter;
import com.example.movieproject1.utilities.JSONTool;
//import com.example.movieproject1.utilities.MovieQueryTask;
import com.example.movieproject1.utilities.NetworkUtils;

import androidx.recyclerview.widget.GridLayoutManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements ImageAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {


    private RecyclerView mShowSomething;
    private ArrayList<Movie> mMovieList2;
    private static String MOVIE_LIST = "movie_array";
    private RecyclerView.Adapter mAdapter;
    private static final int MOVIE_LOADER_ID = 37;
    private String TAG = this.getClass().getSimpleName();
    private final String SEARCH_QUERY_URL_EXTRA = "search_queary_url_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowSomething = (RecyclerView) findViewById(R.id.rv_show_something);

        if (savedInstanceState != null) {
            Log.d(TAG, "get date from savedInstanceState!");
            //mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST);

            //mShowSomething.setHasFixedSize(true);
            //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
            //mShowSomething.setLayoutManager(layoutManager);
            //mAdapter = new ImageAdapter(getApplicationContext(), mMovieList.size(), mMovieList, MainActivity.this);
            //mShowSomething.setAdapter(mAdapter);
        }

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(MOVIE_LIST, mMovieList);
//        Log.d(TAG,"In onSaveInstanceState, the first of the movielist is " + mMovieList.get(0).getTitle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        URL movieSearchUrl;
        switch (itemId) {

            /*         * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)*/

            case R.id.popular:

//                makeMovieSearchQuery(1);
//                new MovieQueryTask(this).execute(1);
                Bundle arg1 = new Bundle();
                arg1.putInt(SEARCH_QUERY_URL_EXTRA, 1);
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, arg1, this);
                return true;
            case R.id.top_rated:
                //menu item 2
//                makeMovieSearchQuery(2);
//                new MovieQueryTask(this).execute(2);
                Bundle arg2 = new Bundle();
                arg2.putInt(SEARCH_QUERY_URL_EXTRA, 2);
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, arg2, this);
                return true;

            case R.id.favorite_movies:
                Bundle arg3 = new Bundle();
                arg3.putInt(SEARCH_QUERY_URL_EXTRA, 3);
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, arg3, this);
                return true;


        }

//        return super.onOptionsItemSelected(item);


        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        return super.onOptionsItemSelected(item);//TODO why return?
    }
/*
    private void makeMovieSearchQuery(int menuItem) {
        Log.d(TAG, "To begin execute a async task.");

        new MovieQueryTask(this).execute(menuItem);

    }*/


    @Override
    public void onListItemClick(int clickedItemIndex) {

//        Log.d(TAG, "onListItemClick is called");
        Class destinationActivity = DetailActivity.class;
        Intent intent = new Intent(MainActivity.this, destinationActivity);
        intent.putExtra("Movie", mMovieList2.get(clickedItemIndex));

        startActivity(intent);
    }


    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {

            ArrayList<Movie> mMovieList;

            @Override
            protected void onStartLoading() {

                if (mMovieList != null) {
                    deliverResult(mMovieList);
                } else {
                    //TODO show progress bar
                    forceLoad();
                }

            }

            @Nullable
            @Override
            public ArrayList<Movie> loadInBackground() {


                Log.d(TAG, "Background Downloading!");
                int menuItem;
                if (args != null) {
                    menuItem = args.getInt(SEARCH_QUERY_URL_EXTRA);
                } else {
                    menuItem = 1;
                }
//                int menuItem = 1;//TODO WE NEED A PREFERENCE
                switch (menuItem) {

                    case 3:
                        Log.d(TAG, "menu item is 3!");
                        Cursor cursor = getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);
                        if (cursor == null) {
                            Log.d(TAG, "the cursor is null!");
                            return null;
                        }
                        ArrayList<Movie> movieFromDatabase = new ArrayList<Movie>();
                        Log.d(TAG,"the number of favorite movies in a cursor is: " +String.valueOf(cursor.getCount()));
                        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                            Log.d(TAG, "it's the nth time in a loop: " + String.valueOf(cursor.getPosition()));
                            Movie m = new Movie(cursor.getString(1), cursor.getString(2),cursor.getString(3));//TODO change into to constant
                            movieFromDatabase.add(m);
                        }
                        Log.d(TAG,"the number of favorite movies in a Array List is: " +String.valueOf(movieFromDatabase.size()));

                        return movieFromDatabase;
                    default:
                        Log.d(TAG,"menuitem is not 3");
                        URL movieSearchUrl = NetworkUtils.buildMovieListUrl(menuItem);
                        String movieSearchResults = null;
                        try {
                            movieSearchResults = NetworkUtils.getResponseFromHttpUrl(movieSearchUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return JSONTool.parseMovieListJson(movieSearchResults);

                }
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Movie> data) {
                mMovieList = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mMovieList2 = data;
        mShowSomething.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mShowSomething.setLayoutManager(layoutManager);
        mAdapter = new ImageAdapter(getApplicationContext(), data.size(), data, this);
        mShowSomething.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }

   /* public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    public void setMovieList(ArrayList<Movie> mMovieList) {
        this.mMovieList = mMovieList;
    }*/

    public RecyclerView getShowSomething() {
        return mShowSomething;
    }

    public void setAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

}
