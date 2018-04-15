package com.meredithbayne.toppopularmovies.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meredithbayne.toppopularmovies.R;
import com.meredithbayne.toppopularmovies.model.Review;
import com.meredithbayne.toppopularmovies.model.ReviewList;
import com.meredithbayne.toppopularmovies.viewholder.ReviewViewHolder;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private ReviewList mReviewList;

    public ReviewAdapter(ReviewList reviews) {
        this.mReviewList = reviews;
    }

    @Override
    @NonNull
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final Review review = mReviewList.getReviews().get(position);

        holder.mAuthor.setText(review.getAuthor());
        holder.mDescription.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.getReviews().size();
    }
}
