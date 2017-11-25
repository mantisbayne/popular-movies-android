package com.meredithbayne.toppopularmovies.util;

import java.net.URL;

/**
 * Created by meredithbayne on 11/24/17.
 */

public class Movie {
    private Integer id;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private Integer voteCount;
    private Double popularity;

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Double getPopularity() {
        return popularity;
    }
}
