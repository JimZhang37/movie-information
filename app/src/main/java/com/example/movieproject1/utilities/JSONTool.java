package com.example.movieproject1.utilities;


//import android.graphics.Movie;
//import android.widget.LinearLayout;

import com.example.movieproject1.model.Movie;
import com.example.movieproject1.model.Review;
import com.example.movieproject1.model.Trailer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;


public class JSONTool {
    final static String TOTAL_RESULTS = "total_results";
    final static String RESULTS = "results";
    final static String ID = "id";
    final static String POSTER_PATH = "poster_path";
    final static String POSTER_Main_PATH = "http://image.tmdb.org/t/p/w185/";
    final static String TITLE = "title";
    final static String SYNOPSIS = "overview";
    final static String USER_RATING = "vote_average";
    final static String RELEASE_DATA = "release_date";

    //    final static String TRAILER_MOVIE_ID = "id";
    final static String TRAILER_MOVIE_RESULTS = "results";
    final static String TRAILER_ID = "id";
    final static String TRAILER_KEY = "key";
    final static String TRAILER_NAME = "name";
    final static String TRAILER_SITE = "site";
    final static String TRAILER_TYPE = "type";

    //    final static String TRAILER_MOVIE_ID = "id";
    final static String REVIEW_MOVIE_RESULTS = "results";
    final static String REVIEW_AUTHOR = "author";
    final static String REVIEW_CONTENT = "content";
    final static String REVIEW_ID = "id";
    final static String REVIEW_URL = "url";


    public static ArrayList<Movie> parseMovieListJson(String json) {
        ArrayList<Movie> mMovieList = new ArrayList<Movie>();
        try {
            JSONObject jObject = new JSONObject(json);

            int i_total_results = jObject.optInt(TOTAL_RESULTS);
            JSONArray j_results = jObject.getJSONArray(RESULTS);
            if (j_results != null) {
                for (int i = 0; i < j_results.length(); i++) {
                    JSONObject result = (JSONObject) j_results.get(i);
                    String movieID = result.getString(ID);
                    String movieImage = POSTER_Main_PATH + result.getString(POSTER_PATH);
                    String movieTitle = result.getString(TITLE);
                    String movieSynopsis = result.getString(SYNOPSIS);
                    String movieUserRating = result.getString(USER_RATING);
                    String movieReleaseData = result.getString(RELEASE_DATA);
                    Movie movie = new Movie(movieID
                            , movieImage
                            , movieTitle
                            , movieSynopsis
                            , movieUserRating
                            , movieReleaseData);
                    mMovieList.add(movie);
                }
            }


            return mMovieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Trailer> parseTrailerListJson(String json) {
        ArrayList<Trailer> mTrailer = new ArrayList<Trailer>();

        try {
            JSONObject jObject = new JSONObject(json);


            JSONArray j_results = jObject.getJSONArray(TRAILER_MOVIE_RESULTS);
            if (j_results != null) {
                for (int i = 0; i < j_results.length(); i++) {
                    JSONObject result = (JSONObject) j_results.get(i);
                    String trailerID = result.getString(TRAILER_ID);
                    String trailerKey = result.getString(TRAILER_KEY);
                    String trailerName = result.getString(TRAILER_NAME);
                    String trailerSite = result.getString(TRAILER_SITE);
                    String trailerType = result.getString(TRAILER_TYPE);
                    Trailer trailer = new Trailer(trailerID
                            , trailerKey
                            , trailerName
                            , trailerSite
                            , trailerType);
                    mTrailer.add(trailer);
                }
            }


            return mTrailer;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Review> parseReviewListJson(String json) {
        ArrayList<Review> mReview = new ArrayList<Review>();

        try {
            JSONObject jObject = new JSONObject(json);


            JSONArray j_results = jObject.getJSONArray(REVIEW_MOVIE_RESULTS);
            if (j_results != null) {
                for (int i = 0; i < j_results.length(); i++) {
                    JSONObject result = (JSONObject) j_results.get(i);
                    String reviewAuthor = result.getString(REVIEW_AUTHOR);
                    String reviewContent = result.getString(REVIEW_CONTENT);
                    String reviewID = result.getString(REVIEW_ID);
                    String reviewURL = result.getString(REVIEW_URL);

                    Review review = new Review(reviewAuthor
                            , reviewContent
                            , reviewID
                            , reviewURL
                    );
                    mReview.add(review);
                }
            }

            return mReview;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
