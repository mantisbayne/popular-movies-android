package com.meredithbayne.toppopularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meredithbayne.toppopularmovies.model.Movie;
import com.meredithbayne.toppopularmovies.model.ReviewList;
import com.meredithbayne.toppopularmovies.view.ReviewAdapter;

public class ReviewsFragment extends Fragment {
    private Movie movie;
    private ReviewList reviewList;
    private ReviewAdapter reviewAdapter;

    public ReviewsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
