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

    public MovieAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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

        private ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    context.startActivity(intent);
                }
            });

            mImageView = itemView.findViewById(R.id.movie_poster);
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}

