/*
package com.example.movieproject1.utilities;

import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieproject1.MainActivity;

import java.io.IOException;
import java.net.URL;

//TODO WHAT IS THIS<URL, VOID, STRING> MEAN IN JAVA?
public class MovieQueryTask extends AsyncTask<Integer, Void, Void> {
    private MainActivity mainActivity;
    private static final String TAG = "MovieQueryTask";

    public MovieQueryTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // COMPLETED(G)       In order to make your codes reusable and structural, you can consider to refactor your codes and put this class in a separate Java file.
    // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
    @Override
    protected Void doInBackground(Integer... params) {
        Log.d(TAG, "Background Downloading!");
        int menuItem = params[0];
        URL movieSearchUrl = NetworkUtils.buildMovieListUrl(menuItem);
        String movieSearchResults = null;
        try {
            movieSearchResults = NetworkUtils.getResponseFromHttpUrl(movieSearchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainActivity.setMovieList(JSONTool.parseMovieListJson(movieSearchResults));

        return null;
    }

    // COMPLETED (3) Override onPostExecute to display the results in the TextView
    @Override
    protected void onPostExecute(Void avoid) {
        Log.d(TAG, "onPostExecute");

        mainActivity.getShowSomething().setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mainActivity.getApplicationContext(), 2);
        mainActivity.getShowSomething().setLayoutManager(layoutManager);
        mainActivity.setAdapter(new ImageAdapter(mainActivity.getApplicationContext(), mainActivity.getMovieList().size(), mainActivity.getMovieList(), mainActivity));
        mainActivity.getShowSomething().setAdapter(mainActivity.getAdapter());

    }


}
*/
