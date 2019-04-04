package com.example.moviesappfirststage.ui.mainActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviesappfirststage.R;
import com.example.moviesappfirststage.models.Movie;
import com.example.moviesappfirststage.utils.JSonParser;
import com.example.moviesappfirststage.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    private List<Movie> movies;
    private ProgressBar progressBar;
    private TextView connectionError;
    private String sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.pb_recycler_view_progress_bar);
        recyclerView = findViewById(R.id.rv_images_recycler_view);
        connectionError = findViewById(R.id.tv_connection_error);
        sortBy = NetworkUtils.popular;
        start();
        connectionError.setOnClickListener(onClick -> start());
    }

    void start() {
        if (isOnline()) {
            showProgressBar();
            new InternetCheck().execute();
        } else {
            hideProgressBar();
            connectionError.setVisibility(View.VISIBLE);
        }

    }

    // check internet connection is available
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void setRecyclerView(List<Movie> moviesList) {
        moviesAdapter = new MoviesAdapter(moviesList, this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setHasFixedSize(true);
    }

    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class InternetCheck extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean internet) {

            if (internet) {
                new LoadMoviesTask().execute(sortBy);
                connectionError.setVisibility(View.INVISIBLE);
                showProgressBar();
            } else {
                hideProgressBar();
                connectionError.setVisibility(View.VISIBLE);
            }
        }
    }

    class LoadMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... sort_by) {
            URL url = NetworkUtils.urlBuilder(sort_by[0]);
            Log.e(TAG, "doInBackground: url get it " + url.toString());


            try {
                String res = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: response is" + res);
                List<Movie> movies = JSonParser.getMoviesFromJson(res);
                Log.d(TAG, "doInBackground: movies size is " + movies.size());
                return movies;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: " + e.getMessage());
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: " + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Movie> moviesList) {
            super.onPostExecute(movies);
            movies = moviesList;
            Log.e(TAG, "onPostExecute: result is " + movies.size());
            if (movies != null) {
                setRecyclerView(movies);
                hideProgressBar();
            } else {
                Log.e(TAG, "onPostExecute: networking problem");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.popular) {
            sortBy = NetworkUtils.popular;
            start();
        } else if (itemId == R.id.top_rated) {
            sortBy = NetworkUtils.topRated;
            start();
        }
        return super.onOptionsItemSelected(item);
    }

}
