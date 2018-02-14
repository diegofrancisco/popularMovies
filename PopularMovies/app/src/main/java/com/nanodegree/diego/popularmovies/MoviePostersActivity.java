package com.nanodegree.diego.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nanodegree.diego.popularmovies.adapter.MoviePostersListAdapter;

public class MoviePostersActivity extends AppCompatActivity {

    /**
     * The max number of posters shown into the list.
     */
    public final static int MAX_MOVIE_POSTERS_COUNT = 20;

    /**
     * RecyclerView reference.
     */
    private RecyclerView mMoviePosterList;

    /**
     * Movie posters list adapter.
     */
    private MoviePostersListAdapter mMoviePostersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupMoviePosterList();
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
}
