package com.meredithbayne.toppopularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterClickListener {
    public static final String EXTRA_POSTER = "poster";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_MOVIE = "movie";
    private boolean isNoFavoritesVisible = false;

    // Set to US for now
    public static final SimpleDateFormat DISPLAY_DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yy", Locale.US);

    @BindView(R.id.movies_error)
    TextView mErrorMessage;
    @BindView(R.id.loading_indicator)
    ProgressBar mLoading;
    @BindView(R.id.movies_list) RecyclerView mMovieRecyclerView;
    MovieAdapter mMovieAdapter;
    @BindView(R.id.favorites_empty)
    TextView mFavoritesEmpty;

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

        String sortOrder = "popular";
        loadMovieData(API.buildMoviesUrl(sortOrder));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.sort_by_rating) {
            if (isNoFavoritesVisible)
                mFavoritesEmpty.setVisibility(View.INVISIBLE);
            loadMovieData(API.buildMoviesUrl("top_rated"));
            item.setChecked(true);
            return true;
        } else if (itemThatWasClickedId == R.id.sort_by_popularity) {
            if (isNoFavoritesVisible)
                mFavoritesEmpty.setVisibility(View.INVISIBLE);
            loadMovieData(API.buildMoviesUrl("popular"));
            return true;
        } else if (itemThatWasClickedId == R.id.show_favorites) {
            showFavorites();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFavorites() {
        showEmptyFavoritesView();
    }

    private void showEmptyFavoritesView() {
        mFavoritesEmpty.setVisibility(View.VISIBLE);
        mMovieRecyclerView.setVisibility(View.INVISIBLE);
        isNoFavoritesVisible = true;
    }

    private void loadMovieData(URL url) {
        showMoviesDataView();
        new MoviesTask(url).execute();
    }

    private void showMoviesDataView() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    public class MoviesTask extends AsyncTask<String, String, String> {
        private URL url;

        MoviesTask(URL url) {
           this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return API.getResponseFromHttpUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mLoading.setVisibility(View.INVISIBLE);
            MovieList movies;

            try {
                showMoviesDataView();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                movies = gson.fromJson(result, MovieList.class);
                mMovieAdapter.setMovieData(movies.getResults());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        Context context = getBaseContext();
        Intent intent = new Intent(context, MovieDetailsActivity.class);

        Movie movie = mMovieAdapter.getItem(position);

        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putExtra(EXTRA_TITLE, movie.getOriginalTitle());
        intent.putExtra(EXTRA_POSTER, movie.getPosterPath());
        intent.putExtra(EXTRA_RELEASE_DATE, movie.formatDate(DISPLAY_DATE_FORMAT));
        intent.putExtra(EXTRA_RATING, movie.formatVoteAverage());
        intent.putExtra(EXTRA_OVERVIEW, movie.getOverview());
        startActivity(intent);
    }
}