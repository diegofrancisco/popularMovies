package com.nanodegree.diego.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nanodegree.diego.popularmovies.R;

public class MoviePostersListAdapter extends RecyclerView.Adapter {

    /**
     * Reference to the max number of posters shown into the list.
     * TODO Why do i need this?
     */
    private final int mMaxListSize;

    /**
     * Public constructor.
     * @param maxListSize
     *  The max number of posters shown into the list.
     */
    public MoviePostersListAdapter(int maxListSize){
        this.mMaxListSize = maxListSize;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.movie_poster_item, parent, false);

        PosterViewHolder posterViewHolder = new PosterViewHolder(view);

        return posterViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO sets data
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class PosterViewHolder extends RecyclerView.ViewHolder{
        public final ImageView mPosterConteiner;

        public PosterViewHolder(View itemView) {
            super(itemView);
            this.mPosterConteiner = (ImageView) itemView.findViewById(R.id.ivPosterContainer);
        }
    }
}
