package com.nanodegree.diego.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nanodegree.diego.popularmovies.MovieInfo;
import com.nanodegree.diego.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MoviePostersListAdapter extends RecyclerView.Adapter<MoviePostersListAdapter.PosterViewHolder> {

    /**
     * Interface that implements a poster click.
     */
    public interface MoviePosterClickListener{
        void onClick(MovieInfo movieInfo);
    }

    /**
     * Keeps a array containing each movie information.
     */
    private MovieInfo[] movieInfoArray;
    public void setMovieInfoArray(MovieInfo[] movieInfoArray) {
        this.movieInfoArray = movieInfoArray;
        this.notifyDataSetChanged();
    }

    /**
     * Keeps a reference to the poster click handler.
     */
    private final MoviePosterClickListener mMoviePosterClickListener;

    /**
     * Public constructor.
     * @param moviePosterClickListener
     *  An implementation to handle a movie posters click.
     */
    public MoviePostersListAdapter(MoviePosterClickListener moviePosterClickListener) {
        this.mMoviePosterClickListener = moviePosterClickListener;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.movie_poster_item, parent, false);

        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        if(this.movieInfoArray != null && this.movieInfoArray.length > position) {
            Picasso.with(holder.mContext).load(
                    this.movieInfoArray[position].getMoviePosterPath()).into(holder.mPosterConteiner);
        }
    }

    @Override
    public int getItemCount() {
        if(this.movieInfoArray != null) return this.movieInfoArray.length;
        else return 0;
    }

    /**
     * Custumized ViewHolder class.
     */
    public class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final ImageView mPosterConteiner;
        final Context mContext;

        PosterViewHolder(View itemView) {
            super(itemView);
            this.mPosterConteiner = (ImageView) itemView.findViewById(R.id.ivPosterContainer);
            this.mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mMoviePosterClickListener != null){
                mMoviePosterClickListener.onClick(movieInfoArray[getAdapterPosition()]);
            }
        }
    }
}
