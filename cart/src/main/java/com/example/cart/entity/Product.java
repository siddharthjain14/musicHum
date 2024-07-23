package com.example.cart.entity;

import java.util.List;

public class Product {
    private String pid;
    private String title;
    private String artist;
    private String album;
    private int year;
    float lowestCost;
    String lowestCostDID;
    String genre;
    float avgRating;
    List<String> raters;

    public List<String> getRaters() {
        return raters;
    }

    public void setRaters(List<String> raters) {
        this.raters = raters;
    }

    long downloads;
    String coverUrl;
    String mood;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getLowestCost() {
        return lowestCost;
    }

    public void setLowestCost(float lowestCost) {
        this.lowestCost = lowestCost;
    }

    public String getLowestCostDID() {
        return lowestCostDID;
    }

    public void setLowestCostDID(String lowestCostDID) {
        this.lowestCostDID = lowestCostDID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return this.getPid();
    }
}
