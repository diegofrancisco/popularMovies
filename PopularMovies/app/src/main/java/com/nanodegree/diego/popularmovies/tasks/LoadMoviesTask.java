package com.nanodegree.diego.popularmovies.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.nanodegree.diego.popularmovies.MoviePostersActivity;


public class LoadMoviesTask extends AsyncTask<Void, Void, ImageView[]> {

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
    protected ImageView[] doInBackground(Void... params) {
        return new ImageView[0];
    }

    @Override
    protected void onPostExecute(ImageView[] imageViews) {
        this.mParent.hideProgressBar();

        super.onPostExecute(imageViews);
    }
}
