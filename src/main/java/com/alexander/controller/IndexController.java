package com.alexander.controller;

import com.alexander.model.FlickrApiHandler;
import com.alexander.model.pojo.Profile;
import com.alexander.model.pojo.response.ResponseToClient;
import com.alexander.view.jsonView.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by Alexander on 06.12.2017.
 */
@RestController
public class IndexController {

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseToClient postIndex(@RequestBody Profile profile){
        ResponseToClient responseToClient = new ResponseToClient();
        FlickrApiHandler flickrApiHandler = new FlickrApiHandler();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<String> photoId = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        String url = profile.getUrl();
        flickrApiHandler.init(url);
        profile = flickrApiHandler.profile;
        responseToClient.setUserId(profile.getUserId());
        responseToClient.setUsername(profile.getUsername());
        responseToClient.setPhotos(profile.getPhotos());

        /*for (Photo photo: profile.getPhotos()){
            tags.add(photo.getTags());
            photoId.add(photo.getId());
        }*/
        //categories.add(flickrApiHandler.getCategoriesByPhotoID("5026982940"));
        //categories = flickrApiHandler.getAllCategories();
        responseToClient.setTags(tags);

        responseToClient.setCategories(categories);
        responseToClient.setPhotoId(photoId);
        return responseToClient;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(){
        return new ModelAndView("index");
    }
}
