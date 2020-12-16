package de.interhyp.acceptancetestdemo.library.rest;

import de.interhyp.acceptancetestdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestRestClient {

    private static final String POST_USER = "/api/user";
    private static final String GET_USER = "/api/user/{id}";

    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<Void> saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(POST_USER, HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<User> getUser(String id) {
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        final HttpEntity<User> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(GET_USER, HttpMethod.GET, entity, User.class, parameters);
    }
}
