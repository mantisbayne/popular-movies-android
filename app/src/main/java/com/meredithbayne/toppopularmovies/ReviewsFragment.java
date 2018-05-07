package com.meredithbayne.toppopularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meredithbayne.toppopularmovies.view.ReviewAdapter;

public class ReviewsFragment extends Fragment {
    ReviewAdapter adapter;

    public ReviewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_reviews_list_fragment, container,
                false);

        RecyclerView reviewsList = rootView.findViewById(R.id.reviews_list);
        reviewsList.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        reviewsList.setLayoutManager(layoutManager);

        return rootView;
    }
}
