package com.meredithbayne.toppopularmovies.network;

import android.net.Uri;
import android.support.annotation.Nullable;
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

public final class API {
    private static final String TAG = API.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";

    // Path
    private static final String TRAILERS_PATH = "videos";
    private static final String REVIEWS_PATH = "reviews";

    // Query parameters
    private static final String API_KEY_PARAM = "api_key";

    // Get URL for popular movies list request
    @Nullable
    public static URL buildMoviesUrl(String sortByPath) {
        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(sortByPath)
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();
        URL moviesUrl;

        try {
            moviesUrl = new URL(uri.toString());
            Log.v(TAG, "Built URI " + moviesUrl);
            return moviesUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getTrailersResponse(String movieId) throws IOException {
        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(TRAILERS_PATH)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                .build();
        URL trailersUrl;

        try {
            trailersUrl = new URL(uri.toString());
            Log.v(TAG, "Built URI " + trailersUrl);
            return getResponseFromHttpUrl(trailersUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getReviewsResponse(String movieId) throws IOException {
        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                .build();
        URL trailersUrl;

        try {
            trailersUrl = new URL(uri.toString());
            Log.v(TAG, "Built URI " + trailersUrl);
            return getResponseFromHttpUrl(trailersUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
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
