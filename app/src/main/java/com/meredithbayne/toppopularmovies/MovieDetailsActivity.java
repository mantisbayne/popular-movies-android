package com.meredithbayne.toppopularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.movie_details_title)
    TextView mTitle;
    @BindView(R.id.movie_details_release_date)
    TextView mReleaseDate;
    @BindView(R.id.movie_details_rating)
    TextView mRating;
    @BindView(R.id.movie_details_overview)
    TextView mOverview;
    @BindView(R.id.movie_details_poster)
    ImageView mPoster;
    @BindView(R.id.favorite_movie_icon)
    ImageView mFavoriteIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        mTitle.setText(getIntent().getStringExtra(MainActivity.EXTRA_TITLE));
        mReleaseDate.setText(getIntent().getStringExtra(MainActivity.EXTRA_RELEASE_DATE));
        mRating.setText(getIntent().getStringExtra(MainActivity.EXTRA_RATING));
        mOverview.setText(getIntent().getStringExtra(MainActivity.EXTRA_OVERVIEW));

        mFavoriteIcon.findViewById(R.id.favorite_movie_icon);
        mFavoriteIcon.setOnClickListener(this);
        mFavoriteIcon.setImageResource(R.drawable.ic_favorite_border_black_24px);

        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.build().load(getIntent().getStringExtra(MainActivity.EXTRA_POSTER))
                .noFade()
                .placeholder(R.drawable.ic_movie_black_24px)
                .error(R.drawable.ic_error_outline_black_24px)
                .into(mPoster);
    }

    @Override
    public void onClick(View v) {

    }
}
