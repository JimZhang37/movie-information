package com.example.movieproject1.utilities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieproject1.model.Movie;
import com.example.movieproject1.model.Review;
import com.example.movieproject1.model.Trailer;

import java.util.ArrayList;


public class DetailViewModel extends AndroidViewModel {

    private final TrailerLiveData trailerLiveData;
    private final ReviewLiveData reviewLiveData ;
    private final IsFavorateLiveData isFavorate;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        Log.d("DetailViewModel.constructor", application.getContentResolver().toString());
        trailerLiveData = new TrailerLiveData(application);
        reviewLiveData = new ReviewLiveData(application);
        isFavorate = new IsFavorateLiveData(application);

        Log.d("MovieViewModel", "constructor");
    }


    public void setData(Movie movie) {

        trailerLiveData.loadData(movie.getMovieID());
        reviewLiveData.loadData(movie.getMovieID());
        isFavorate.loadData(movie.getMovieID());
    }

    public LiveData<ArrayList<Trailer>> getTrailerData() {
        return trailerLiveData;
    }

    public LiveData<ArrayList<Review>> getReviewData() {
        return reviewLiveData;
    }

    public LiveData<Boolean> getIsFavorate() {
        return isFavorate;
    }

    public void addFavorite(String movieID){
        isFavorate.addFavorite(movieID);
    }

    public void removeFavorite(String movieID){
        isFavorate.removeFavorite(movieID);
    }
}
