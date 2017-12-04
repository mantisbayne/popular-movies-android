package com.meredithbayne.toppopularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meredithbayne.toppopularmovies.network.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Load movie data into the view
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private Context mContext;
    private List<Movie> mMovieData = Collections.emptyList();

    private MovieAdapterClickListener mListener;


    public interface MovieAdapterClickListener {
        void onMovieItemClick(View view, int position);
    }

    public Movie getItem(int id) {
        return mMovieData == null ? null : mMovieData.get(id);
    }

    MovieAdapter(MovieAdapterClickListener listener){
        mListener = listener;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_poster_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieData.get(position);

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.build().load(movie.getPosterPath())
                .noFade()
                .into(holder.mImageView, new Callback() {
                    @Override public void onSuccess() {

                    }
                    @Override public void onError() {}
                });
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        private ViewHolder(final View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.movie_poster);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMovieItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}

