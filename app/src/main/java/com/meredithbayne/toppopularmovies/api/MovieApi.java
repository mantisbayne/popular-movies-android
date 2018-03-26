package com.meredithbayne.toppopularmovies.api;

import com.meredithbayne.toppopularmovies.model.Movie;
import com.meredithbayne.toppopularmovies.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Network calls to GET top_rated, popular, videos, and reviews for movies
 */

interface MovieApi {
    @GET("movie/popular")
    Call<MovieList> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieList> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/{movieId}?append_to_response=videos,reviews")
    Call<Movie> getMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
