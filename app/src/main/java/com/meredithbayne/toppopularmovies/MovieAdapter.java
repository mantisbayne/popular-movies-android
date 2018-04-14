package com.meredithbayne.toppopularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meredithbayne.toppopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Load movie data into the view
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private Context mContext;
    private List<Movie> mMovieData = Collections.emptyList();
    private MovieAdapterClickListener mListener;


    public interface MovieAdapterClickListener {
        void onMovieItemClick(int position);
    }

    public Movie getItem(int id) {
        return mMovieData == null ? null : mMovieData.get(id);
    }

    MovieAdapter(MovieAdapterClickListener listener){
        mListener = listener;
    }

    @Override
    @NonNull
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_poster_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieData.get(position);

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.build().load(movie.getPosterPath())
                .noFade()
                .placeholder(R.drawable.ic_movie_black)
                .error(R.drawable.ic_error_outline_black)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView mImageView;

        private ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMovieItemClick(getAdapterPosition());
                }
            });
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}

