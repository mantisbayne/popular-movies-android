package com.meredithbayne.toppopularmovies.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meredithbayne.toppopularmovies.R;
import com.meredithbayne.toppopularmovies.model.Trailer;
import com.meredithbayne.toppopularmovies.model.TrailerList;
import com.meredithbayne.toppopularmovies.viewholder.TrailerViewHolder;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private Context mContext;
    private TrailerList mTrailerList;
    private static final String TRAILER_BASE_URL = "http://www.youtube.com/watch?v=";

    TrailerAdapter(Context mContext, TrailerList mTrailerList) {
        this.mContext = mContext;
        this.mTrailerList = mTrailerList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = mTrailerList.getTrailers().get(position);

        holder.mTrailerTitle.setText(trailer.getName());
        holder.mPlayButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(TRAILER_BASE_URL + trailer.getKey()));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mTrailerList.getTrailers().size();
    }
}
