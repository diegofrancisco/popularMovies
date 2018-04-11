package com.nanodegree.diego.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nanodegree.diego.popularmovies.adapter.MoviePostersListAdapter;
import com.nanodegree.diego.popularmovies.keys.ConstantKeys;
import com.nanodegree.diego.popularmovies.tasks.LoadMoviesTask;

public class MoviePostersActivity extends AppCompatActivity implements MoviePostersListAdapter.MoviePosterClickListener {

    /**
     * Request url for the Movie db API.
     */
    private final static String MOVIE_DB_MOST_POPULAR_URL_REQUEST = String.format("https://api.themoviedb.org/3/movie/popular?api_key=%s",
            ConstantKeys.MOVIE_DB_API_KEY);
    private final static String MOVIE_DB_TOP_RATED_URL_REQUEST = String.format("https://api.themoviedb.org/3/movie/top_rated?api_key=%s",
            ConstantKeys.MOVIE_DB_API_KEY);

    /**
     * Previously selected list order.
     */
    private final static String SELECTED_LIST_ORDER_BUNDLE_STATE = "SELECTED_LIST_ORDER";

    /**
     * Current movie order.
     */
    public enum MovieOrder {
        POPULARITY,
        TOP_RATED
    }

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

    /**
     * Error TextView reference.
     */
    private TextView mErrorTextView;

    /**
     * Keeps a reference to the current movie order.
     */
    private MovieOrder mMovieOrder = MovieOrder.POPULARITY;

    /**
     * Sets the adapter array containing each movie information.
     */
    public void setMovieInfoArray(MovieInfo[] movieInfoArray) {
        this.mMoviePostersAdapter.setMovieInfoArray(movieInfoArray);
    }

    public void showProgressBar() {
        this.mProgressiveBar.setVisibility(View.VISIBLE);
        this.mErrorTextView.setVisibility(View.INVISIBLE);
        this.mMoviePosterList.setVisibility(View.INVISIBLE);
    }

    public void showPosterList() {
        this.mProgressiveBar.setVisibility(View.INVISIBLE);
        this.mErrorTextView.setVisibility(View.INVISIBLE);
        this.mMoviePosterList.setVisibility(View.VISIBLE);
    }

    public void showErrorView() {
        this.mProgressiveBar.setVisibility(View.INVISIBLE);
        this.mErrorTextView.setVisibility(View.VISIBLE);
        this.mMoviePosterList.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters_activity);

        // Sets up the layout
        this.mProgressiveBar = (ProgressBar) this.findViewById(R.id.pbLoading);
        this.mErrorTextView = (TextView) this.findViewById(R.id.tvErrorLoadingData);

        this.setupMoviePosterList();

        if(savedInstanceState != null &&
                savedInstanceState.get(SELECTED_LIST_ORDER_BUNDLE_STATE) == MovieOrder.TOP_RATED.toString()){
            this.setTitle(R.string.topRatedMoviesTitle);
            this.mMovieOrder = MovieOrder.TOP_RATED;
        }else {
            this.setTitle(R.string.topPopularMoviesTitle);
        }

        // Loads the data
        this.loadMovieData();
    }

    /**
     * Sets up the movie posters list adapter and its properties.
     */
    private void setupMoviePosterList() {
        this.mMoviePosterList = (RecyclerView) this.findViewById(R.id.rvMoviePosters);

        // will set the posters into a grid arrangement
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                3, LinearLayoutManager.VERTICAL, false);
        this.mMoviePosterList.setLayoutManager(gridLayoutManager);

        this.mMoviePosterList.setHasFixedSize(true);

        this.mMoviePostersAdapter = new MoviePostersListAdapter(this);
        this.mMoviePosterList.setAdapter(this.mMoviePostersAdapter);
    }

    /**
     * Starts the load data thread.
     */
    private void loadMovieData() {
        if (this.mMovieOrder == MovieOrder.POPULARITY) {
            new LoadMoviesTask(this).execute(MOVIE_DB_MOST_POPULAR_URL_REQUEST);
        } else if (this.mMovieOrder == MovieOrder.TOP_RATED) {
            new LoadMoviesTask(this).execute(MOVIE_DB_TOP_RATED_URL_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_poster_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refreshMovies) {
            this.mMoviePostersAdapter.setMovieInfoArray(null);
            this.loadMovieData();
            return true;
        } else if (item.getItemId() == R.id.menuOrder) {
            if (this.mMovieOrder == MovieOrder.POPULARITY) {
                item.setTitle(this.getString(R.string.menuOrderByPopularity));
                this.mMovieOrder = MovieOrder.TOP_RATED;
                this.setTitle(R.string.topRatedMoviesTitle);
            } else if (this.mMovieOrder == MovieOrder.TOP_RATED) {
                item.setTitle(this.getString(R.string.menuOrderByTopRated));
                this.mMovieOrder = MovieOrder.POPULARITY;
                this.setTitle(R.string.topPopularMoviesTitle);
            }
            this.mMoviePostersAdapter.setMovieInfoArray(null);
            this.loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(MovieDetailsActivity.MOVIE_INFO_EXTRA, movieInfo);
        this.startActivity(movieDetailsIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SELECTED_LIST_ORDER_BUNDLE_STATE, this.mMovieOrder.toString());
        super.onSaveInstanceState(outState);
    }
}
