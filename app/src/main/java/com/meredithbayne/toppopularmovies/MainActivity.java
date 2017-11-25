package com.meredithbayne.toppopularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meredithbayne.toppopularmovies.network.API;
import com.meredithbayne.toppopularmovies.network.MovieList;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
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

        mMovieAdapter = new MovieAdapter(getApplicationContext());
        mMovieRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showMoviesDataView();
        new MoviesTask().execute();
    }

    private void showMoviesDataView() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    public class MoviesTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            URL moviesUrl = API.buildPopularMoviesUrl();

            try {
                String response = API.getResponseFromHttpUrl(moviesUrl);
                return response;
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
}