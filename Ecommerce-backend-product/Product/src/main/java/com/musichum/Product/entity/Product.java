package com.musichum.Product.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection="productTable")
public class Product {
    @Id
    String pid;
    String title;
    String artist;
    String album;
    int year;
    int lowestCost;
    String did; //changed from lowestCostDID
    String genre;
    float avgRating;
    long downloads;
    String coverUrl;
    String mood;
    int counter=0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    // List<Products> getRecommendations();

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

    public void setLowestCost(int lowestCost) {
        this.lowestCost = lowestCost;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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
}
