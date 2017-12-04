package com.meredithbayne.toppopularmovies.network;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;

/**
 * Movie object representation of response from server
 */

public class Movie {
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    @SerializedName("id")
    private Integer id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("vote_average")
    private Double voteAverage;

    /**
     * No args constructor for use in serialization
     *
     */
    public Movie() {
    }

    /**
     * Implementation of a Movie object
     * @param id - id for the film object
     * @param releaseDate - date film was released to public
     * @param posterPath - path to image file for movie poster
     * @param originalTitle - title of film in its original language
     * @param voteAverage - rating based on user votes
     * @param popularity - average popularity
     * @param overview - plot summary for the film
     */
    public Movie(Integer id, String originalTitle, String overview,
                   Double popularity, String posterPath, Date releaseDate, Double voteAverage) {
        super();

        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return POSTER_BASE_URL + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String formatDate(DateFormat dateFormat) {
        return dateFormat.format(releaseDate);
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String formatVoteAverage() {
        return voteAverage.toString() + "/10";
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
