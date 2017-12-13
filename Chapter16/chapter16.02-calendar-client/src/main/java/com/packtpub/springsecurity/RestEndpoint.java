package com.packtpub.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * http://localhost:8888/
 * http://localhost:8888/
 *
 * http://localhost:8888/beans
 *
 */
@RestController
public class RestEndpoint {

    @Autowired
    private OAuth2RestOperations template;

    @Value("${oauth.resource:http://localhost:8080/api}")
    private String baseUrl;


    @GetMapping("/beans")
    public  List<Map<String, ?>> home() {
        @SuppressWarnings("unchecked")
        List<Map<String, ?>> result = template.getForObject("http://localhost:8080/admin/beans", List.class);
        return result;
    }

    @GetMapping("/")
    public  String apiCheck() {
        @SuppressWarnings("unchecked")
        String result = template.getForObject(baseUrl, String.class);
        return result;
    }

} // The End...
