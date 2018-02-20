package com.nanodegree.diego.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.diego.popularmovies.util.JSONUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MovieDetailsActivity extends AppCompatActivity {

    /**
     * Movie JSON string extra identifier.
     */
    public static final String MOVIE_JSON_STRING_EXTRA = "JSON_STRING_EXTRA";

    /**
     * ImageView that contains the poster.
     */
    ImageView mPosterContainer;

    /**
     * TextView that shows the movie title.
     */
    TextView mTitleTextView;

    /**
     * TextView that shows the movie synopsis.
     */
    TextView mSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        MovieInfo movieInfo = null;

        this.mPosterContainer = (ImageView) this.findViewById(R.id.ivPoster);
        this.mTitleTextView = (TextView) this.findViewById(R.id.tvMovieTitle);
        this.mSynopsisTextView = (TextView) this.findViewById(R.id.tvMovieSynopsis);

        Intent intent = this.getIntent();
        if(intent.hasExtra(MOVIE_JSON_STRING_EXTRA)){
            String jSonString = intent.getStringExtra(MOVIE_JSON_STRING_EXTRA);

            try {
                movieInfo = JSONUtil.getSingleMovieInfo(new JSONObject(jSonString));
            }catch(JSONException ex){
                ex.printStackTrace();
            }

            if(movieInfo != null){
                this.mTitleTextView.setText(movieInfo.getMovieTitle());
                this.mSynopsisTextView.setText(movieInfo.getMovieDescription());
                Picasso.with(this).load(movieInfo.getMoviePosterPath()).into(this.mPosterContainer);
            }
        }
    }
}
