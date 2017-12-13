package com.packtpub.springsecurity.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This displays the welcome screen that shows what will be happening in this chapter.
 *
 * @author Rob Winch
 *
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "{'message': 'welcome to the JBCP Calendar Application'}";
    }

    @GetMapping("/api")
    public String api() {
        return "{'message': 'welcome to the JBCP Calendar Application API'}";
    }

} // The End...
