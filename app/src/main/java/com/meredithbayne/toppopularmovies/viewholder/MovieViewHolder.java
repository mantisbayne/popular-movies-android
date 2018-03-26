package com.meredithbayne.toppopularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.meredithbayne.toppopularmovies.R;

/**
 * View holder for movie data
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public ImageView mMoviePoster;
    private ProgressBar mProgressBar;

    public MovieViewHolder(View view) {
        super(view);

        mMoviePoster = view.findViewById(R.id.movie_poster);
        mProgressBar = view.findViewById(R.id.loading_indicator);
    }

    public void isLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }
}
