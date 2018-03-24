package com.alexander.model.pojo.response;

import com.alexander.view.jsonView.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.alexander.model.pojo.Photo;

import java.util.ArrayList;

/**
 * Created by Alexander on 14.12.2017.
 */
public class ResponseToClient {
    @JsonView(Views.Public.class)
    private String username = new String();
    @JsonView(Views.Public.class)
    private String userId = new String();
    @JsonView(Views.Public.class)
    private ArrayList<Photo> photos = new ArrayList<>();
    @JsonView(Views.Public.class)
    private ArrayList<String> tags = new ArrayList<>();
    @JsonView(Views.Public.class)
    private ArrayList<String> categories = new ArrayList<>();
    @JsonView(Views.Public.class)
    private ArrayList<String> photoId = new ArrayList<>();

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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getPhotoId() {
        return photoId;
    }

    public void setPhotoId(ArrayList<String> photoId) {
        this.photoId = photoId;
    }
}
