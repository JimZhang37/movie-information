package com.example.movieproject1.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class Movie implements Parcelable {


    private String mMovieID;
    private String mImage;
    private String mTitle;
    private String mSynopsis;
    private String mUserRating;
    private String mReleaseDate;

    public Movie(String mMovieIDs, String mImages, String mTitle, String mSynopsis, String mUserRating, String mReleaseDate) {
        this.mMovieID = mMovieIDs;
        this.mImage = mImages;
        this.mTitle = mTitle;
        this.mSynopsis = mSynopsis;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
    }

    public Movie(String mMovieIDs, String mTitle, String mImage) {
        this.mMovieID = mMovieIDs;
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mSynopsis = null;
        this.mUserRating = null;
        this.mReleaseDate = null;
    }

    public Movie(Parcel parcel) {
        this.mMovieID = parcel.readString();
        this.mImage = parcel.readString();
        this.mTitle = parcel.readString();
        this.mSynopsis = parcel.readString();
        this.mUserRating = parcel.readString();
        this.mReleaseDate = parcel.readString();
    }

    public String getMovieID() {
        return mMovieID;
    }

    public String getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mMovieID='" + mMovieID + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mSynopsis='" + mSynopsis + '\'' +
                ", mUserRating='" + mUserRating + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMovieID);
        parcel.writeString(mImage);
        parcel.writeString(mTitle);
        parcel.writeString(mSynopsis);
        parcel.writeString(mUserRating);
        parcel.writeString(mReleaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}






/*
package com.example.movieproject1.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class MovieList implements Parcelable {
*/
/* Completed (F)   To learn more, in order to save and recover the dynamic data using savedInstanceState, you can let this class implement Parcelable. By doing so, you can optimize your app to save dynamic data/state efficiently.
    https://developer.android.com/reference/android/os/Parcelable.html*//*


    private int mMovieCount;
    private List<String> mMovieIDs;
    private List<String> mImages;
    private List<String> mTitle;
    private List<String> mSynopsis;
    private List<String> mUserRating;
    private List<String> mReleaseDate;

    private final String TAG = this.getClass().getSimpleName();
*/
/*    public MovieList(){

    }*//*


 */
/*    public MovieList(int movieCount, List<String> movieIDs, List<String> images){
        mMovieCount = movieCount;
        mMovieIDs = movieIDs;
        mImages = images;
        Log.d(TAG, "Number of movie is " + String.valueOf(mMovieCount));
    }*//*


    public MovieList(int movieCount, List<String> movieIDs, List<String> images, List<String> title, List<String> synopsis, List<String> userRatig, List<String> releaseDate){
        mMovieCount = movieCount;
        mMovieIDs = movieIDs;
        mImages = images;
        mTitle = title;
        mSynopsis = synopsis;
        mUserRating = userRatig;
        mReleaseDate = releaseDate;
        Log.d(TAG, "Number of movie is " + String.valueOf(mMovieCount));
    }

    public int getMovieCount(){
        return mMovieCount;
    }

    public String getMovieImageByPosition(int position){
        return mImages.get(position);
    }

    public String getMovieIDByPosition(int position){

        return mMovieIDs.get(position);
    }

    public String getMovieTitleByPosition(int position) {
        return mTitle.get(position);
    }

    public String getMovieSynopsisByPosition(int position){
        return mSynopsis.get(position);
    }

    public String getMovieUserRatingByPosition(int position){
        return mUserRating.get(position);
    }

    public  String getMovieReleaseDateByPosition(int position){
        return mReleaseDate.get(position);
    }

    public int getCurrentMovieCount(){
        return mImages.size();
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<MovieList> CREATOR
            = new Parcelable.Creator<MovieList>() {
        @Override
        public MovieList createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public MovieList[] newArray(int i) {
            return new MovieList[0];
        }
    };


    //endregion

}
*/
