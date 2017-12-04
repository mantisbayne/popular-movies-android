package com.meredithbayne.toppopularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverwiew;
    private ImageView mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitle = findViewById(R.id.movie_details_title);
        mReleaseDate = findViewById(R.id.movie_details_release_date);
        mRating = findViewById(R.id.movie_details_rating);
        mOverwiew = findViewById(R.id.movie_details_overview);
        mPoster = findViewById(R.id.movie_details_poster);

        mTitle.setText(getIntent().getStringExtra(MainActivity.EXTRA_TITLE));
        mReleaseDate.setText(getIntent().getStringExtra(MainActivity.EXTRA_RELEASE_DATE));
        mRating.setText(getIntent().getStringExtra(MainActivity.EXTRA_RATING));
        mOverwiew.setText(getIntent().getStringExtra(MainActivity.EXTRA_OVERVIEW));

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.build().load(getIntent().getStringExtra(MainActivity.EXTRA_POSTER))
                .noFade()
                .into(mPoster, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
    }
}
