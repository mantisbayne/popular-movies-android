package com.meredithbayne.toppopularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;

/**
 * Movie object representation of response from server
 */

public class Movie {
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("vote_average")
    private Double voteAverage;

    /**
     * Implementation of a Movie object
     * @param releaseDate - date film was released to public
     * @param posterPath - path to image file for movie poster
     * @param originalTitle - title of film in its original language
     * @param voteAverage - rating based on user votes
     * @param overview - plot summary for the film
     */
    public Movie(String originalTitle, String overview,
                   String posterPath, Date releaseDate, Double voteAverage) {
        super();

        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return POSTER_BASE_URL + posterPath;
    }

    public String formatDate(DateFormat dateFormat) {
        return dateFormat.format(releaseDate);
    }

    public String formatVoteAverage() {
        return voteAverage.toString() + "/10";
    }
}
