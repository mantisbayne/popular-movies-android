package com.meredithbayne.toppopularmovies.api;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.meredithbayne.toppopularmovies.BuildConfig;
import com.meredithbayne.toppopularmovies.model.ReviewList;
import com.meredithbayne.toppopularmovies.model.TrailerList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Create request and response handling configurations
 */

public final class API {
    private static final String TAG = API.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";

    private static final String TRAILERS_PATH = "videos";
    private static final String REVIEWS_PATH = "reviews";
    private static final String MOVIE_ID_PATH = "movie_id";
    private static final String API_KEY_PARAM = "api_key";

    private static final String TRAILER_BASE_URL = "https://www.youtube.com/";
    private static final String TRAILER_IMAGE = "https://img.youtube.com/vi/";

    private static final String TRAILER_WATCH_PATH = "watch";
    private static final String TRAILER_VIDEO_PATH = "v";
    private static final String DEFAULT_QUALITY_IMAGE = "default.jpg";

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

    public static URL buildTrailersUrl(String trailerId) {
        Uri trailerUri = Uri.parse(TRAILER_BASE_URL).buildUpon()
                .appendEncodedPath(TRAILER_WATCH_PATH)
                .appendQueryParameter(TRAILER_VIDEO_PATH, trailerId)
                .build();

        URL trailersUrl;

        try {
            trailersUrl = new URL(trailerUri.toString());
            Log.v(TAG, "Built URI " + trailerUri);
            return trailersUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri getTrailerImageUri(String videoId) {
        return Uri.parse(TRAILER_IMAGE).buildUpon()
                .appendEncodedPath(videoId)
                .appendEncodedPath(DEFAULT_QUALITY_IMAGE)
                .build();
    }

    public interface Api {
        @GET(MOVIES_BASE_URL + TRAILERS_PATH + "?" + API_KEY_PARAM + "="
                + BuildConfig.MOVIE_DB_API_KEY)
        Call<TrailerList> getVideosForMovie(@Path(MOVIE_ID_PATH) int movieId);

        @GET(MOVIES_BASE_URL + REVIEWS_PATH + "?" + API_KEY_PARAM + "=" +
                BuildConfig.MOVIE_DB_API_KEY)
        Call<ReviewList> getReviewsForMovie(@Path(MOVIE_ID_PATH) int movieId);
    }
}
