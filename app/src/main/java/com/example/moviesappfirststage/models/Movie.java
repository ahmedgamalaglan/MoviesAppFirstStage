package com.example.moviesappfirststage.models;

import java.io.Serializable;

public class Movie implements Serializable {



    private String overview;
    private String title;
    private double vote_average;
    private String poster_path;
    private String release_date;

    public Movie(String overview, String title, double vote_average, String poster_path, String release_date) {

        this.overview = overview;
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
