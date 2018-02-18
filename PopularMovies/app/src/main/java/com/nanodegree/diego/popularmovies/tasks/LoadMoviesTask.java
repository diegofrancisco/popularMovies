package com.nanodegree.diego.popularmovies.tasks;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.nanodegree.diego.popularmovies.MoviePostersActivity;
import com.nanodegree.diego.popularmovies.adapter.MoviePostersListAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class LoadMoviesTask extends AsyncTask<String, Void, ImageView[]> {

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
    protected ImageView[] doInBackground(String... params) {
        URL url;
        String httpResponse;

        if(!TextUtils.isEmpty(params[0])){
            url = this.buildURL(params[0]);

            if(url != null) {
                httpResponse = this.getHttpResponse(url);


            }
        }

        return new ImageView[0];
    }

    @Override
    protected void onPostExecute(ImageView[] imageViews) {
        this.mParent.hideProgressBar();

        super.onPostExecute(imageViews);
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
}
