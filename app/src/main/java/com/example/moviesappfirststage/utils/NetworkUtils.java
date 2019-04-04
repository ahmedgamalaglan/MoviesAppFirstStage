package com.example.moviesappfirststage.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    public static final String apiKey = "your api key";
    public static final String imagesBaseUrl = "http://image.tmdb.org/t/p/w185/";
    public static final String moviesBaseUrl = "http://api.themoviedb.org/3/movie/";
    public static final String topRated = "top_rated";
    public static final String popular = "popular";


    public static URL urlBuilder(String sort_by) {
        Uri uriBuilder = Uri.parse(moviesBaseUrl).buildUpon()
                .appendPath(sort_by)
                .appendQueryParameter("api_key", apiKey)
                .build();
        URL url;
        try {
            url = new URL(uriBuilder.toString());

        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean checkNetworkAccess() {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 3000);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
