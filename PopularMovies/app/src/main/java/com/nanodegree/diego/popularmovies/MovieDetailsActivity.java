package com.nanodegree.diego.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    /**
     * Movie JSON string extra identifier.
     */
    public static final String MOVIE_INFO_EXTRA = "MOVIE_INFO_EXTRA";

    /**
     * ImageView that contains the poster.
     */
    private ImageView mPosterContainer;

    /**
     * TextView that shows the movie title.
     */
    private TextView mTitleTextView;

    /**
     * TextView that shows the movie synopsis.
     */
    private TextView mSynopsisTextView;

    /**
     * TextView that shows the movie release date.
     */
    private TextView mReleaseDateTextView;

    /**
     * TextView that shows the movie vote average.
     */
    private TextView mVoteAverageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.setTitle(getString(R.string.movieDetailTitle));
        MovieInfo movieInfo;

        this.mPosterContainer = (ImageView) this.findViewById(R.id.ivPoster);
        this.mTitleTextView = (TextView) this.findViewById(R.id.tvMovieTitle);
        this.mSynopsisTextView = (TextView) this.findViewById(R.id.tvMovieSynopsis);
        this.mVoteAverageTextView = (TextView) this.findViewById(R.id.tvMovieVoteAverage);
        this.mReleaseDateTextView = (TextView) this.findViewById(R.id.tvMovieDateRelease);

        Intent intent = this.getIntent();
        if(intent.hasExtra(MOVIE_INFO_EXTRA)){
            movieInfo = intent.getParcelableExtra(MOVIE_INFO_EXTRA);

            if(movieInfo != null){
                this.mTitleTextView.setText(movieInfo.getMovieTitle());
                this.mSynopsisTextView.setText(movieInfo.getMovieDescription());
                this.mReleaseDateTextView.setText(movieInfo.getMovieReleaseDate());
                this.mVoteAverageTextView.setText(movieInfo.getMovieVoteAverage());
                Picasso.with(this).load(movieInfo.getMoviePosterPath()).into(this.mPosterContainer);
            }
        }
    }
}
