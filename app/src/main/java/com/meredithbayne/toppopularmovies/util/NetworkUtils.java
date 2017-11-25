package com.meredithbayne.toppopularmovies.util;

import android.net.Uri;
import android.util.Log;

import com.meredithbayne.toppopularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Create request and response handling configurations
 */

public final class NetworkUtils {
    private static Movie movie = new Movie();
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String POPULAR_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String TOP_RATED_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";

    // Query parameters
    private static final String API_KEY_PARAM = "api_key";

    // Get URL for popular movies list request
    public static URL buildPopularMoviesUrl() {
        Uri uri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();
        URL popularUrl = null;

        try {
            popularUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + popularUrl);

        return popularUrl;
    }

    // Get URL for top rated movies list request
    public static URL buildTopRatedMoviesUrl() {
        Uri uri = Uri.parse(TOP_RATED_MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();
        URL topRatedUrl = null;

        try {
            topRatedUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + topRatedUrl);

        return topRatedUrl;
    }

    // Get URL for poster image to pass to Picasso
    public static URL buildMoviePostersUrl() {
        Uri uri = Uri.parse(POSTER_BASE_URL).buildUpon()
                    .appendPath(movie.getPosterPath())
                    .build();
        URL posterUrl = null;

        try {
            posterUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + posterUrl);

        return posterUrl;
    }

    // Get the response from the movie database server
    public static String getResponseFromHttpUrl(URL url) throws IOException {
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
}
