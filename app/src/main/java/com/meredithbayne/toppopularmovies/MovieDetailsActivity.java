package com.meredithbayne.toppopularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meredithbayne.toppopularmovies.data.MovieContract;
import com.meredithbayne.toppopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String STATE_IS_FAVORITED = "is_favorited";

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

    private Movie movie;
    private boolean isFavorite;

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

        FragmentManager fragmentManager = getSupportFragmentManager();

        ReviewsFragment reviewsFragment = new ReviewsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.movie_reviews_container, reviewsFragment)
                .commit();

        if (savedInstanceState == null)
            movie = getIntent().getParcelableExtra(MainActivity.EXTRA_MOVIE);

        loadPoster();
        if (movie != null)
            updateViews();
        else
            showError();
    }

    private void loadPoster() {
        Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
        builder.build().load(getIntent().getStringExtra(MainActivity.EXTRA_POSTER))
                .noFade()
                .placeholder(R.drawable.ic_movie_black)
                .error(R.drawable.ic_error_outline_black)
                .into(mPoster);
    }

    private void updateViews() {
        updateFavoriteUi();

        displayReviews();
        displayTrailers();
    }

    private void displayReviews() {

    }

    private void displayTrailers() {

    }

    public void onClickFavoriteIcon(View view) {
        updateFavorites();
    }

    private void updateFavorites() {
        if (isFavorite) {
            deleteFromFavorites(movie.getId());
            isFavorite = false;
        } else {
            addToFavorites(movie.getId(), movie.getOriginalTitle(), movie.getPosterPath(),
                    movie.getVoteAverage(), movie.formatDate(MainActivity.DISPLAY_DATE_FORMAT),
                    movie.getOverview());
            isFavorite = true;
        }
        onFavoritesUpdateComplete();
    }

    private void onFavoritesUpdateComplete() {
        updateFavoriteUi();
    }

    private void addToFavorites(int id, String originalTitle, String posterPath, Double voteAverage,
                                String releaseDate, String overview) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.COLUMN_ID, id);
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, originalTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        }

    }

    private void updateFavoriteUi() {
        if (isFavorite)
            mFavoriteIcon.setImageResource(R.drawable.ic_favorite_black);
        else
            mFavoriteIcon.setImageResource(R.drawable.ic_favorite_border_black);
    }

    private void deleteFromFavorites(int id) {
        // Remove from the database
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(id)).build();
        getContentResolver().delete(uri, null, null);
        getContentResolver().notifyChange(uri, null);

        // Notify the user
        Toast.makeText(this, movie.getOriginalTitle() + " " +
                getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show();
    }

    private boolean isFavoriteMovie() {
        final Cursor cursor;
        cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null,
                "movie_id=?", new String[]{String.valueOf(movie.getId())}, null);

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_FAVORITED, isFavorite);
    }

    private void showError() {

    }
}
