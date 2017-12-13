package com.packtpub.springsecurity.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This displays the welcome screen that shows what will be happening in this chapter.
 *
 * @author Mick Knutson
 *
 */
@RestController
public class WelcomeController {

    @GetMapping("/foo")
    public String welcome() {
        return "Hello Microservice";
    }

} // The End...
