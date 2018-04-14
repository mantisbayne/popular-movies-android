package com.meredithbayne.toppopularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meredithbayne.toppopularmovies.R;

/**
 * View holder for review data for a movie
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    public TextView mAuthor;
    public TextView mDescription;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        mAuthor = itemView.findViewById(R.id.movie_review_author);
        mDescription = itemView.findViewById(R.id.movie_review_description);
    }
}

