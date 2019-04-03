package com.example.moviesappfirststage;


import com.example.moviesappfirststage.utils.NetworkUtils;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    String s;


    @Test
    public void networking() {
        try {
            s = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.urlBuilder(NetworkUtils.popular));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("http://api.themoviedb.org/3/movie/top_rated?api_key=e651012316c7720eab43087b7a10ee1a", s);
    }
}