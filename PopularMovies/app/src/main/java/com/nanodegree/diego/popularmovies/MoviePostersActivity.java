package com.nanodegree.diego.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.nanodegree.diego.popularmovies.adapter.MoviePostersListAdapter;
import com.nanodegree.diego.popularmovies.keys.ConstantKeys;
import com.nanodegree.diego.popularmovies.tasks.LoadMoviesTask;

public class MoviePostersActivity extends AppCompatActivity {

    /**
     * The max number of posters shown into the list.
     */
    public final static int MAX_MOVIE_POSTERS_COUNT = 20;

    /**
     * Request url for the Movie db API.
     */
    public final static String MOVIE_DB_MOST_POPULAR_URL_REQUEST = String.format("https://api.themoviedb.org/3/movie/popular?api_key=%s",
            ConstantKeys.MOVIE_DB_API_KEY);

    /**
     * RecyclerView reference.
     */
    private RecyclerView mMoviePosterList;

    /**
     * Movie posters list adapter.
     */
    private MoviePostersListAdapter mMoviePostersAdapter;

    /**
     * Progressive bar indicator.
     */
    private ProgressBar mProgressiveBar;

    public void showProgressBar(){
        this.mProgressiveBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        this.mProgressiveBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters_activity);

        // Sets up the layout
        this.mProgressiveBar = (ProgressBar) this.findViewById(R.id.pbLoading);
        this.setupMoviePosterList();

        // Loads the data
        this.loadMovieData();
    }

    /**
     * Sets up the movie posters list adapter and its properties.
     */
    private void setupMoviePosterList(){
        this.mMoviePosterList = (RecyclerView) this.findViewById(R.id.rvMoviePosters);

        // will set the posters into a grid arrangement
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.mMoviePosterList.setLayoutManager(layoutManager);

        this.mMoviePosterList.setHasFixedSize(true);

        this.mMoviePostersAdapter = new MoviePostersListAdapter(MAX_MOVIE_POSTERS_COUNT);
        this.mMoviePosterList.setAdapter(this.mMoviePostersAdapter);
    }

    /**
     * Starts the load data thread.
     */
    private void loadMovieData(){
        // TODO create a error view to show in case the data load fails

        // TODO create a method to load the query preferences (order, popularity, etc...)

        new LoadMoviesTask(this).execute(MOVIE_DB_MOST_POPULAR_URL_REQUEST);
    }
}
