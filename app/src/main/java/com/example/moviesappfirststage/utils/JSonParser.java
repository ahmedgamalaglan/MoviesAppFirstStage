package com.example.moviesappfirststage.utils;

import android.support.annotation.NonNull;

import com.example.moviesappfirststage.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSonParser {

    private static final String TAG = "JSonParser";

    public static List<Movie> getMoviesFromJson(@NonNull String jsonString) throws JSONException {

        List<Movie> movieList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);

        JSONArray moviesArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < moviesArray.length(); i++) {

            JSONObject object = moviesArray.optJSONObject(i);

            String title = object.optString("title");
            String overview = object.optString("overview");
            String releaseDate = object.optString("release_date");
            String posterPath = object.optString("poster_path");
            double voteRange = object.optDouble("vote_average");

            Movie movie = new Movie(overview, title, voteRange, posterPath, releaseDate);
            movieList.add(movie);
        }
        return movieList;

    }
}
