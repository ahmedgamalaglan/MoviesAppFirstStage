package com.example.moviesappfirststage.ui.detailsActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviesappfirststage.R;
import com.example.moviesappfirststage.models.Movie;
import com.example.moviesappfirststage.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {


    private ImageView movieImage;
    private TextView movieTitle;
    private TextView releaseDate;
    private RatingBar movieRatingBar;
    private TextView movieReview;
    private TextView movieRating;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        initComponents();
        setComponentsValues();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(movie.getTitle());
    }


    private void initComponents() {
        movieImage = findViewById(R.id.iv_movie_image);
        movieTitle = findViewById(R.id.tv_movie_title);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        movieReview = findViewById(R.id.tv_movie_review);
        movieRatingBar = findViewById(R.id.rb_movie_ratingBar);
        movieRating = findViewById(R.id.tv_movie_rating);
    }

    private void setComponentsValues() {
        Picasso.with(this).load(NetworkUtils.imagesBaseUrl + movie.getPoster_path()).into(movieImage);
        movieTitle.setText(movie.getTitle());
        releaseDate.setText("Released at : " + movie.getRelease_date());
        movieReview.setText(movie.getOverview());
        movieRatingBar.setRating((float) movie.getVote_average());
        movieRating.setText(movie.getVote_average() + " / 10");
    }
}
