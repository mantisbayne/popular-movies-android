package com.meredithbayne.toppopularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meredithbayne.toppopularmovies.api.API;
import com.meredithbayne.toppopularmovies.model.Movie;
import com.meredithbayne.toppopularmovies.model.MovieList;
import com.meredithbayne.toppopularmovies.view.MovieAdapter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterClickListener, LoaderCallbacks<MovieList> {

    private static final int MOVIE_LOADER_ID = 0;

    public static final String EXTRA_POSTER = "poster";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_MOVIE = "movie";
    private static final String STATE_SORTED_BY = "sorted_by";

    private static final String DATE_PATTERN = "MM/dd/yy";
    private static final String RESPONSE_DATE_PATTERN = "MM/dd/yy";

    // TODO improve this for internationalization / localization
    public static final SimpleDateFormat DISPLAY_DATE_FORMAT =
            new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private static final String EXTRA_MOVIE_ID = "movie_id";

    @BindView(R.id.movies_error)
    TextView mErrorMessage;
    @BindView(R.id.movies_list)
    RecyclerView mMovieRecyclerView;
    MovieAdapter mMovieAdapter;
    @BindView(R.id.loading_indicator)
    ProgressBar mLoading;

    private String sortedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        String sortedByDefault = getString(R.string.sort_by_popular);

        if (savedInstanceState == null)
            sortedBy = sortedByDefault;
        else
            sortedBy = savedInstanceState.getString(STATE_SORTED_BY, sortedByDefault);

        LoaderCallbacks<MovieList> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.sort_by_rating) {
            sortedBy = getString(R.string.sort_by_rating);
            invalidateData();
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            return true;
        } else if (itemId == R.id.sort_by_popularity) {
            sortedBy = getString(R.string.sort_by_popular);
            invalidateData();
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            return true;
        } else if (itemId == R.id.show_favorites) {
            // TODO implement favorites behavior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMoviesDataView() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<MovieList> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<MovieList>(this) {
            MovieList movies = null;

            @Override
            protected void onStartLoading() {
                if (movies != null)
                    deliverResult(movies);
                else {
                    mLoading.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public MovieList loadInBackground() {
                URL url = API.buildMoviesUrl(sortedBy);
                try {
                    String result = API
                            .getResponseFromHttpUrl(url);
                    Gson gson = new GsonBuilder().setDateFormat(RESPONSE_DATE_PATTERN).create();
                    movies = gson.fromJson(result, MovieList.class);
                    return movies;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(MovieList data) {
                movies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MovieList> loader, MovieList data) {
        mLoading.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMovieData(data.getResults());
        showMoviesDataView();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MovieList> loader) {
        // do nothing - not used
    }

    private void invalidateData() {
        mMovieAdapter.setMovieData(null);
    }

    @Override
    public void onMovieItemClick(int position) {
        Context context = getBaseContext();
        Intent intent = new Intent(context, MovieDetailsActivity.class);

        Movie movie = mMovieAdapter.getItem(position);

        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        intent.putExtra(EXTRA_TITLE, movie.getOriginalTitle());
        intent.putExtra(EXTRA_POSTER, movie.getPosterPath());
        intent.putExtra(EXTRA_RELEASE_DATE, movie.formatDate(DISPLAY_DATE_FORMAT));
        intent.putExtra(EXTRA_RATING, movie.formatVoteAverage());
        intent.putExtra(EXTRA_OVERVIEW, movie.getOverview());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString(STATE_SORTED_BY, sortedBy);
    }
}