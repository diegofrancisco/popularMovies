package com.nanodegree.diego.popularmovies.util;

import android.net.Uri;

import com.nanodegree.diego.popularmovies.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    private final static String MOVIE_DB_JSON_RESULTS = "results";
    private final static String MOVIE_DB_JSON_TITLE = "title";
    private final static String MOVIE_DB_JSON_DESCRIPTION = "overview";
    private final static String MOVIE_DB_JSON_POSTER_PATH = "poster_path";
    private final static String MOVIE_DB_JSON_VOTE_AVERAGE = "vote_average";
    private final static String MOVIE_DB_JSON_RELEASE_DATE = "release_date";

    private final static String MOVIE_DB_POSTER_BASE_PATH = "https://image.tmdb.org/t/p/w185";

    /**
     * Parses the JSON string result from the Movie API.
     * @param json
     *  JSON string.
     * @return
     *  MovieInfo array containing all movies informations.
     */
    public static MovieInfo[] parseMovieDbJSON(String json){
        MovieInfo[] movieInfoArray = null;
        JSONObject rootJSON;
        JSONArray movies;
        JSONObject movieItemJSON;

        try {
            rootJSON = new JSONObject(json);
            if(rootJSON.has(MOVIE_DB_JSON_RESULTS)) {
                movies = rootJSON.getJSONArray(MOVIE_DB_JSON_RESULTS);

                if(movies != null && movies.length() > 0){
                    movieInfoArray = new MovieInfo[movies.length()];

                    for(int i = 0; i < movies.length(); i++){
                        movieItemJSON = movies.getJSONObject(i);

                        // Apends to the result array
                        movieInfoArray[i] = JSONUtil.getSingleMovieInfo(movieItemJSON);
                    }
                }
            }
        }catch(JSONException ex){
            ex.printStackTrace();
        }

        return movieInfoArray;
    }

    /**
     * Parses the JSON of a single movie information.
     * @param movieItemJSON
     *  JSON Object
     * @return
     * MovieInfo instance containing the movie information.
     */
    public static MovieInfo getSingleMovieInfo(JSONObject movieItemJSON){
        MovieInfo movieInfoItem = new MovieInfo();

        try {
            // Gets the movie title information
            if (movieItemJSON.has(MOVIE_DB_JSON_TITLE)) {
                movieInfoItem.setMovieTitle(movieItemJSON.getString(MOVIE_DB_JSON_TITLE));
            }

            // Gets the movie description information
            if (movieItemJSON.has(MOVIE_DB_JSON_DESCRIPTION)) {
                movieInfoItem.setMovieDescription(movieItemJSON.getString(MOVIE_DB_JSON_DESCRIPTION));
            }

            // Gets the movie poster path information
            if (movieItemJSON.has(MOVIE_DB_JSON_POSTER_PATH)) {
                movieInfoItem.setMoviePosterPath(
                        Uri.parse(MOVIE_DB_POSTER_BASE_PATH).buildUpon().appendPath(
                                movieItemJSON.getString(MOVIE_DB_JSON_POSTER_PATH).substring(1)).build().toString());
            }

            // Gets the movie vote average information
            if (movieItemJSON.has(MOVIE_DB_JSON_VOTE_AVERAGE)) {
                movieInfoItem.setMovieVoteAverage(movieItemJSON.getString(MOVIE_DB_JSON_VOTE_AVERAGE));
            }

            // Gets the movie release date information
            if (movieItemJSON.has(MOVIE_DB_JSON_RELEASE_DATE)) {
                movieInfoItem.setMovieReleaseDate(movieItemJSON.getString(MOVIE_DB_JSON_RELEASE_DATE));
            }
        }catch(JSONException ex){
            ex.printStackTrace();
        }

        return movieInfoItem;
    }
}
