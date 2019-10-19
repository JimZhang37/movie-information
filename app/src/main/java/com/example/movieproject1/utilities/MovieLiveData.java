package com.example.movieproject1.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


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



}
