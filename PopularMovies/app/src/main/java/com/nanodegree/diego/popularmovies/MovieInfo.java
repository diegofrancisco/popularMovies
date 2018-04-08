package com.nanodegree.diego.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class keeps all the information for a single movie.
 */
public class MovieInfo implements Parcelable {

    /**
     * Constructor.
     */
    public MovieInfo(){    }

    /**
     * Constructor.
     */
    protected MovieInfo(Parcel in) {
        movieTitle = in.readString();
        movieDescription = in.readString();
        moviePosterPath = in.readString();
    }

    /**
     * Parcelable CREATOR field.
     */
    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    /**
     * Movie title information.
     */
    private String movieTitle;
    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Movie description.
     */
    private String movieDescription;
    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    /**
     * Movie poster url path.
     */
    private String moviePosterPath;
    public String getMoviePosterPath() {
        return moviePosterPath;
    }
    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieDescription);
        dest.writeString(moviePosterPath);
    }
}
