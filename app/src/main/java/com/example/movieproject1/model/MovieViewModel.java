package com.example.movieproject1.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Trailer>> mTrailerList;
    private MutableLiveData<ArrayList<Review>> mReviewList;

    public LiveData<ArrayList<Trailer>> getTrailers() {
        if(mTrailerList == null){
            mTrailerList = new MutableLiveData<ArrayList<Trailer>>();
            loadTrailers();
        }
        return mTrailerList;
    }

    private void loadTrailers(){

        return;//TODO
    }

    public LiveData<ArrayList<Review>> getReviews(){
        if(mReviewList == null){
            mReviewList = new MutableLiveData<ArrayList<Review>>();
        }
        return mReviewList;
    }

    private void loadReviews(){
        return;//TODO
    }
}
