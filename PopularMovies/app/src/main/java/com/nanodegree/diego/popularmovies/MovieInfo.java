package com.nanodegree.diego.popularmovies;

/**
 * This class keeps all the information for a single movie.
 */
public class MovieInfo {

    /**
     * Constructor.
     */
    public MovieInfo(){    }

    /**
     * Keeps a reference to the full JSON information.
     */
    private String originalJSONInfo;
    public String getOriginalJSONInfo() {
        return originalJSONInfo;
    }
    public void setOriginalJSONInfo(String originalJSONInfo) {
        this.originalJSONInfo = originalJSONInfo;
    }

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
}
