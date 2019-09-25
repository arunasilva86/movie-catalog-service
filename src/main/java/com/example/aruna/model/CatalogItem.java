package com.example.aruna.model;

public class CatalogItem {

    private String movieName;
    private String movieDescription;
    private int ratingValue;

    public CatalogItem() {
    }

    public CatalogItem(String movieName, String movieDescription, int ratingValue) {
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.ratingValue = ratingValue;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}
