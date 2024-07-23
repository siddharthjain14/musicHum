package com.example.searchProduct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;


@SolrDocument(collection="searchProduct")
public class SearchItem {
    @Id
    @Indexed(name="id")
    private String id;
    @Indexed(name="type")
    private String type;
    @Indexed(name="title")
    private String title;
    @Indexed(name="albumName")
    private String albumName;
    @Indexed(name="artist")
    private String artist;
    @Indexed(name="year")
    private int year;
    @Indexed(name="genre")
    private String genre;

    public void setLowestCost(float lowestCost) {
        this.lowestCost = lowestCost;
    }

    @Indexed(name="cost",type="Integer")
    private float lowestCost;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "######"+getId()+" "+getAlbumName()+" "+getArtist()+" "+getGenre()+" "+getTitle()+" "+getType()+" ";
    }
}
