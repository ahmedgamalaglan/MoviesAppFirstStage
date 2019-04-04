package com.example.moviesappfirststage.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviesappfirststage.R;
import com.example.moviesappfirststage.models.Movie;
import com.example.moviesappfirststage.ui.detailsActivity.DetailsActivity;
import com.example.moviesappfirststage.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies;
    Context context;

    public MoviesAdapter(List<Movie> movies, Context c) {
        this.movies = movies;
        this.context = c;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movie_item, viewGroup, false);
        return new MoviesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        moviesViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //================================================================================================
    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_movie_item);
            imageView.setOnClickListener(this);
        }

        void bind(int index) {
            Picasso.with(context).load(NetworkUtils.imagesBaseUrl + movies.get(index).getPoster_path()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Movie sentMovie=movies.get(getAdapterPosition());
            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("movie",sentMovie);
            context.startActivity(intent);
        }
    }

    public interface Clickable {
        void onItemClicked();
    }

}