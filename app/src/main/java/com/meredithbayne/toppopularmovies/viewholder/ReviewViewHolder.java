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
    public TextView mContent;

    public ReviewViewHolder(View itemView) {
        super(itemView);

//        mTvReviewAuthor = itemView.findViewById(R.id.tvReviewAuthor);
//        mTvReviewContent = itemView.findViewById(R.id.tvReviewContent);
    }
}

