package com.alexander.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alexander.model.config.Config;
import com.alexander.model.naiveBayesClassifier.ClassifierLauncher;
import com.alexander.model.naiveBayesClassifier.base.Classifier;
import com.alexander.model.pojo.Photo;
import com.alexander.model.pojo.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 07.12.2017.
 */
public class FlickrApiHandler {
    public Profile profile = new Profile();

    public String getCategoriesByPhotoID(String photoId) {
        String result = new String();
        ClassifierLauncher classifierLauncher = new ClassifierLauncher();
        Classifier<String, String> classifier = classifierLauncher.getTrainingClassifier();
        for (Photo photo : profile.getPhotos()) {
            if (photoId.equals(photo.getId())) {
                String tags = photo.getTags();
                result = classifierLauncher.getClassifyResult(classifier, tags);
            }
        }
        return result;
    }

    public ArrayList<String> getAllCategories() {
        ArrayList<String> result = new ArrayList<String>();
        ClassifierLauncher classifierLauncher = new ClassifierLauncher();
        Classifier<String, String> classifier = classifierLauncher.getTrainingClassifier();
        ArrayList<Photo> photos = profile.getPhotos();

        /*for (Photo photo : photos) {
            String tags = photo.getTags();
            result.add(classifierLauncher.getClassifyResult(classifier, tags));
        }*/

        photos.stream().parallel().forEach(photo -> {
            String tags = photo.getTags();
            result.add(classifierLauncher.getClassifyResult(classifier, tags));
        });
        return result;
    }

    public void init(String url) {
        try {
            initUserIdAndUsername(url);
            initPhotos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPhotos() throws IOException {
        int currentPage = 0;
        int maxPage = 0;
        HttpHandler httpHandler = new HttpHandler();
        ArrayList<Photo> photos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String user_id = profile.getUserId();
        body.add("api_key", Config.MY_APPLICATION_KEY);
        body.add("user_id", user_id);
        body.add("format", "json");
        body.add("per_page", "300");
        body.add("nojsoncallback", "1");
        body.add("extras", "tags");
        do {
            currentPage++;
            body.add("page", String.valueOf(currentPage));
            ResponseEntity<String> responseEntity = httpHandler.getResponseFromPostRequest(Config.URL_FOR_GET_PHOTOS_BY_USER_ID, body);
            JsonNode root = mapper.readTree(responseEntity.getBody());
            String allPhotos = getAllPhoto(root);
            maxPage = getMaxPage(root);
            photos.addAll(mapper.readValue(allPhotos, new TypeReference<List<Photo>>() {
            }));
        } while (currentPage < maxPage);
        profile.setPhotos(photos);
    }

    private void initUserIdAndUsername(String url) throws IOException {
        String userId = new String();
        String username = new String();
        HttpHandler httpHandler = new HttpHandler();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("api_key", Config.MY_APPLICATION_KEY);
        body.add("url", url);
        body.add("format", "json");
        body.add("nojsoncallback", "1");
        ResponseEntity<String> responseEntity = httpHandler.getResponseFromPostRequest(Config.URL_FOR_GET_USER_ID, body);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseEntity.getBody());
        userId = getUserId(root);
        username = getUsername(root);
        profile.setUserId(userId);
        profile.setUsername(username);
    }

    private String getAllPhoto(JsonNode root) {
        String photos = root.path("photos").path("photo").toString();
        return photos;
    }

    private int getMaxPage(JsonNode root) {
        int maxPage = root.path("photos").path("pages").intValue();
        return maxPage;
    }

    private String getUsername(JsonNode root) {
        String username = root.path("user").path("username").path("_content").textValue();
        return username;
    }

    private String getUserId(JsonNode root) {
        String userId = root.path("user").get("id").textValue();
        return userId;
    }
}
