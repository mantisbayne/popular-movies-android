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
import com.meredithbayne.toppopularmovies.network.API;
import com.meredithbayne.toppopularmovies.network.Movie;
import com.meredithbayne.toppopularmovies.network.MovieList;

import java.net.URL;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickListener {
    public static final String EXTRA_POSTER = "poster";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_RELEASE_DATE = "releaseDate";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_OVERVIEW = "overview";

    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

    private TextView mErrorMessage;
    private ProgressBar mLoading;
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecyclerView = findViewById(R.id.recyclerview_movies);
        mErrorMessage = findViewById(R.id.movies_error);
        mLoading = findViewById(R.id.loading_indicator);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData(API.buildPopularMoviesUrl());
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
            loadMovieData(API.buildTopRatedMoviesUrl());
            return true;
        } else if (itemThatWasClickedId == R.id.sort_by_popularity) {
            loadMovieData(API.buildPopularMoviesUrl());
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        public MoviesTask(URL url) {
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

        intent.putExtra(EXTRA_TITLE, movie.getOriginalTitle());
        intent.putExtra(EXTRA_POSTER, movie.getPosterPath());
        intent.putExtra(EXTRA_RELEASE_DATE, movie.formatDate(DISPLAY_DATE_FORMAT));
        intent.putExtra(EXTRA_RATING, movie.formatVoteAverage());
        intent.putExtra(EXTRA_OVERVIEW, movie.getOverview());
        startActivity(intent);
    }
}