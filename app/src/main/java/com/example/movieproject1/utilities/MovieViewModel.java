package com.example.movieproject1.utilities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieproject1.model.Movie;


import java.util.ArrayList;


public class MovieViewModel extends AndroidViewModel {

    private final MovieLiveData movieLiveData;



    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieLiveData = new MovieLiveData(application);

        Log.d("MovieViewModel", "constructor");
    }



    public void setData(int type) {
        switch (type) {
            case 1:
                movieLiveData.loadData(1);
                return;
            case 2:
                movieLiveData.loadData(2);
                return;
            default:
                movieLiveData.loadData(1);
                return;

        }

    }

    public LiveData<ArrayList<Movie>> getData() {
        return movieLiveData;
    }
}
