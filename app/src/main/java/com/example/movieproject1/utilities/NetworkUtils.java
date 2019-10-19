/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.movieproject1.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final String TAG = this.getClass().getSimpleName();

    final static String MOVIE_BASE_URL =
            "https://api.themoviedb.org/3/movie/";

    final static String PARAM_APIKEY = "api_key";

    final static String PATH_POPULAR = "popular";

    final static String PATH_TOP_RATED = "top_rated";

    final static String PATH_VIDEOS = "videos";

    final static String PATH_REVIEWS = "reviews";

    final static String API_KEY = "758f975f610e3d276c8f2364e5052672";

    /**
     * Builds the URL used to query GitHub.
     *
     * @param menuID Choices between popular and top_rated.
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildMovieListUrl(int menuID) {

        String path = PATH_POPULAR;//default value is popular
        if (menuID == 1) {
            path = PATH_POPULAR;
        } else if (menuID == 2) {
            path = PATH_TOP_RATED;
        }

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(path)
                .appendQueryParameter(PARAM_APIKEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d("getResponseFromHttpUrl", "A new effort to fetch data from network");//I want to know if app make network request everytime configuratioin changes
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static URL buildTrailersUrl(String movieID) {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath(PATH_VIDEOS)
                .appendQueryParameter(PARAM_APIKEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildReviewsUrl(String movieID) {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(movieID)
                .appendPath(PATH_REVIEWS)
                .appendQueryParameter(PARAM_APIKEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}