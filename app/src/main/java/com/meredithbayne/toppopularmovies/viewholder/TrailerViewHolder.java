package com.meredithbayne.toppopularmovies.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meredithbayne.toppopularmovies.R;

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    public ImageView mPlayButton;
    public TextView mTrailerTitle;

    public TrailerViewHolder(View itemView) {
        super(itemView);

        mPlayButton = itemView.findViewById(R.id.trailer_play_button);
        mPlayButton = itemView.findViewById(R.id.trailer_title);
    }
}
