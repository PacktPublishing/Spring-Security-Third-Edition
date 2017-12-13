package com.packtpub.springsecurity.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * This displays the welcome screen that shows what will be happening in this chapter.
 *
 * @author Mick Knutson
 *
 * http://localhost:8888/
 * http://localhost:8888/api
 * http://localhost:8888/beans
 *
 * http://localhost:8888/events/
 * http://localhost:8888/events/my
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome(HttpServletRequest request) {
        return getNetworDetails(request);
    }

    private String getNetworDetails(HttpServletRequest request) {

        String remoteAddress = request.getRemoteAddr();

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            sb.append("'IP Address': '").append(ip).append("'").append(", ");
            sb.append("'hostname': '").append(hostname).append("'").append(", ");
            sb.append("'remoteAddress': '").append(remoteAddress).append("'");

            sb.append("'OTHER URIs': [");
            sb.append("'").append(baseUrl).append("/', ");
            sb.append("'").append(baseUrl).append("/beans', ");
            sb.append("'").append(baseUrl).append("/api', ");
            sb.append("'").append(baseUrl).append("/events/', ");
            sb.append("'").append(baseUrl).append("/events/my'");
            sb.append("]");

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        sb.append("}");
        return sb.toString();
    }

    @Autowired
    private OAuth2RestOperations template;

    @Value("${base.url:http://localhost:8888}")
    private String baseUrl;

    @Value("${oauth.url:http://localhost:8080}")
    private String baseOauthUrl;


    @GetMapping("/beans")
    public List<Map<String, ?>> home() {
        @SuppressWarnings("unchecked")
        List<Map<String, ?>> result = template.getForObject(baseOauthUrl+"/admin/beans", List.class);
        return result;
    }

    @GetMapping("/api")
    public  String apiCheck() {
        @SuppressWarnings("unchecked")
        String result = template.getForObject(baseOauthUrl+"/api", String.class);
        return result;
    }

} // The End...
