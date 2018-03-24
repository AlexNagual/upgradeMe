package com.alexander.model.pojo;

import java.util.ArrayList;

/**
 * Created by Alexander on 07.12.2017.
 */
public class Profile {
    private String url = new String();
    private String username = new String();
    private String userId = new String();
    private ArrayList<Photo> photos = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

}
