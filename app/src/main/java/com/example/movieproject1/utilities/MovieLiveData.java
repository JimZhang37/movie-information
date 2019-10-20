package com.example.movieproject1.utilities;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import com.example.movieproject1.model.FavoriteMovieContract;
import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import com.example.movieproject1.model.FavoriteMovieContract;
import com.example.movieproject1.model.Movie;


public class MovieLiveData extends LiveData<ArrayList<Movie>> {
    private String TAG = this.getClass().getSimpleName();
    private final Context context;


    public MovieLiveData(Context context) {
        this.context = context;
        Log.d("movielivedata constructor","called");

        loadData(1);
    }

    @Override
    protected void onActive() {

    }

    @Override
    protected void onInactive() {

    }

    public void loadData(int type) {


        new  AsyncTask<Integer, Void, ArrayList<Movie>>() {
            @Override
            protected ArrayList<Movie> doInBackground(Integer... type) {
                int menuID = type[0];
                Log.d(TAG, "menu item is !"+ String.valueOf(menuID));
                URL urlMovie = NetworkUtils.buildMovieListUrl(menuID);
                Log.d(TAG, "async the movie URL is : " + urlMovie.toString());

                String movieSearchResults = null;

                try {
                    movieSearchResults = NetworkUtils.getResponseFromHttpUrl(urlMovie);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Movie> movies = JSONTool.parseMovieListJson(movieSearchResults);
                Log.d("doInBackground: the first movie is", movies.get(0).getTitle());
                return movies;
            }

            @Override
            protected void onPostExecute(ArrayList<Movie> movies) {
                Log.d("onPostExecute: the first movie is", movies.get(0).getTitle());
                setValue(movies);
            }
        }.execute(type);


    }

public void loadDatabase(){

    new  AsyncTask<Void, Void, ArrayList<Movie>>() {
        @Override
        protected ArrayList<Movie> doInBackground(Void... type) {
            Log.d(TAG, "menu item is 3!");
            Cursor cursor = context.getContentResolver().query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            if (cursor == null) {
                Log.d(TAG, "the cursor is null!");
                return null;
            }
            ArrayList<Movie> movieFromDatabase = new ArrayList<Movie>();
            Log.d(TAG, "the number of favorite movies in a cursor is: " + String.valueOf(cursor.getCount()));
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Log.d(TAG, "it's the nth time in a loop: " + String.valueOf(cursor.getPosition()));
                Movie m = new Movie(cursor.getString(1), cursor.getString(2), cursor.getString(3));//TODO change into to constant
                movieFromDatabase.add(m);
            }
            Log.d(TAG, "the number of favorite movies in a Array List is: " + String.valueOf(movieFromDatabase.size()));

            return movieFromDatabase;


        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            Log.d("onPostExecute: the first movie is", movies.get(0).getTitle());
            setValue(movies);
        }
    }.execute();




}

}
