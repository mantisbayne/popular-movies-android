package com.meredithbayne.toppopularmovies.view;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meredithbayne.toppopularmovies.MainActivity;
import com.meredithbayne.toppopularmovies.R;
import com.meredithbayne.toppopularmovies.model.Movie;
import com.meredithbayne.toppopularmovies.model.MovieList;
import com.meredithbayne.toppopularmovies.viewholder.MovieViewHolder;

import retrofit2.Call;

/**
 * Adapter for favorites view
 */

public class FavoritesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private MainActivity mMainActivity;

    private MovieList mFavoriteMovies;
    private Call<Movie> movieRequest;

    public FavoritesAdapter(MainActivity activity, Cursor cursor) {
        this.mMainActivity = activity;
        mFavoriteMovies = new MovieList();
        loadMovies(cursor);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private void loadMovies(Cursor cursor) {

    }
}
