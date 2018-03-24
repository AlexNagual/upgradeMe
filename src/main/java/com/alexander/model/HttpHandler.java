package com.alexander.model;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Alexander on 13.12.2017.
 */
public class HttpHandler {
    protected HttpEntity getRequest(MultiValueMap<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        return request;
    }

    protected ResponseEntity getResponseFromPostRequest(String url, MultiValueMap<String, String> body) {
        HttpEntity<MultiValueMap<String, String>> request = getRequest(body);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return responseEntity;
    }
}
