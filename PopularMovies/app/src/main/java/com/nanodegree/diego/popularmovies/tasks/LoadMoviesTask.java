package com.nanodegree.diego.popularmovies.tasks;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.nanodegree.diego.popularmovies.MovieInfo;
import com.nanodegree.diego.popularmovies.MoviePostersActivity;
import com.nanodegree.diego.popularmovies.adapter.MoviePostersListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class LoadMoviesTask extends AsyncTask<String, Void, MovieInfo[]> {

    private final static String MOVIE_DB_JSON_RESULTS = "results";
    private final static String MOVIE_DB_JSON_TITLE = "title";
    private final static String MOVIE_DB_JSON_DESCRIPTION = "overview";
    private final static String MOVIE_DB_JSON_POSTER_PATH = "poster_path";

    private final static String MOVIE_DB_POSTER_BASE_PATH = "https://image.tmdb.org/t/p/w185";

    /**
     * Reference to the movie poster activity.
     */
    private final MoviePostersActivity mParent;

    /**
     * Public constructor.
     * @param parent
     *  Reference to the movie poster activity.
     */
    public LoadMoviesTask(MoviePostersActivity parent){
        this.mParent = parent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.mParent.showProgressBar();
    }

    @Override
    protected MovieInfo[] doInBackground(String... params) {
        URL url;
        String httpResponse;
        MovieInfo[] movieInfoArray = null;

        if(!TextUtils.isEmpty(params[0])){
            url = this.buildURL(params[0]);

            if(url != null) {
                httpResponse = this.getHttpResponse(url);
                if(httpResponse != null) {
                    movieInfoArray = this.parseMovieDbJSON(httpResponse);
                }
            }
        }

        return movieInfoArray;
    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfoArray) {
        if(movieInfoArray != null) {
            this.mParent.setMovieInfoArray(movieInfoArray);
            this.mParent.showPosterList();
        }else {
            this.mParent.showErrorView();
        }

        super.onPostExecute(movieInfoArray);
    }

    /**
     * Get the Http response from a specified url.
     * @param url
     *      The url where the http response will fetch from.
     * @return
     *      String containing the http response.
     */
    private String getHttpResponse(URL url){
        HttpURLConnection httpConnection = null;
        String httpResponse = null;
        InputStream urlInputStream;

        try {
            // Opens http connection
            httpConnection = (HttpURLConnection)url.openConnection();

            // Gets the inputstream used to read from the urhttpl connection
            urlInputStream = httpConnection.getInputStream();

            // Use a scanner to retrieve the whole string
            Scanner scanner = new Scanner(urlInputStream);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()){
                httpResponse = scanner.next();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }finally {
            if(httpConnection != null){
                httpConnection.disconnect();
            }
        }

        return httpResponse;
    }

    /**
     * Builds a URL upon a url string address.
     * @param baseUrlString
     *  url string address.
     * @return
     * Instance of URL class.
     */
    private URL buildURL(String baseUrlString){
        Uri uri;
        URL url = null;

        uri = Uri.parse(baseUrlString).buildUpon().build();

        try {
            url = new URL(uri.toString());
        }
        catch (MalformedURLException ex){
            ex.printStackTrace();
        }

        return url;
    }

    /**
     * Parses the JSON string result from the Movie API.
     * @param json
     *  JSON string.
     * @return
     *  MovieInfo array containing all movies informations.
     */
    private MovieInfo[] parseMovieDbJSON(String json){
        MovieInfo[] movieInfoArray = null;
        MovieInfo movieInfoItem;
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
                        movieInfoItem = new MovieInfo();
                        movieItemJSON = movies.getJSONObject(i);

                        // Gets the movie title information
                        if(movieItemJSON.has(MOVIE_DB_JSON_TITLE)){
                            movieInfoItem.setMovieTitle(movieItemJSON.getString(MOVIE_DB_JSON_TITLE));
                        }

                        // Gets the movie description information
                        if(movieItemJSON.has(MOVIE_DB_JSON_DESCRIPTION)){
                            movieInfoItem.setMovieDescription(movieItemJSON.getString(MOVIE_DB_JSON_DESCRIPTION));
                        }

                        // Gets the movie poster path information
                        if(movieItemJSON.has(MOVIE_DB_JSON_POSTER_PATH)){
                            movieInfoItem.setMoviePosterPath(
                                    Uri.parse(MOVIE_DB_POSTER_BASE_PATH).buildUpon().appendPath(
                                            movieItemJSON.getString(MOVIE_DB_JSON_POSTER_PATH).substring(1)).build().toString());
                        }

                        // Apends to the result array
                        movieInfoArray[i] = movieInfoItem;
                    }
                }
            }
        }catch(JSONException ex){
            ex.printStackTrace();
        }

        return movieInfoArray;
    }
}
