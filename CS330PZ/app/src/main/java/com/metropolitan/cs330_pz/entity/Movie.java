package com.metropolitan.cs330_pz.entity;

public class Movie {
    private int id;
    private String name;
    private String genre;
    private String description;
    private int rating;
    private byte[] img;

    public Movie() {
    }

    public Movie(int id, String name, String genre, String description, int rating, byte[] img) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.rating = rating;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
