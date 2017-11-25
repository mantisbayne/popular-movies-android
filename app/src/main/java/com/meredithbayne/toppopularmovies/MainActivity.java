package com.meredithbayne.toppopularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.meredithbayne.toppopularmovies.util.Movie;
import com.meredithbayne.toppopularmovies.util.NetworkUtils;

import org.json.JSONException;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mErrorMessage;
    private ProgressBar mLoading;
    private RecyclerView mMovieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieRecyclerView = findViewById(R.id.recyclerview_movies);
        mErrorMessage = findViewById(R.id.movies_error);
        mLoading = findViewById(R.id.loading_indicator);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);

        mMovieRecyclerView.setLayoutManager(layoutManager);
        mMovieRecyclerView.setHasFixedSize(true);
        MovieAdapter adapter = new MovieAdapter(getApplicationContext());
        mMovieRecyclerView.setAdapter(adapter);
    }

    public class MoviesTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            URL movieRequestUrl = NetworkUtils.buildMoviePostersUrl();

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                return jsonResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mLoading.setVisibility(View.INVISIBLE);
            Movie movie;

            try {
                showMovieDataView();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showMovieDataView() {

    }

    public String[] getMoviePosterUrlsFromJson(Context context, String moviesJsonStr)
            throws JSONException {
        String [] posterUrls;

        URL moviesListUrl = NetworkUtils.buildPopularMoviesUrl();

    }
}
