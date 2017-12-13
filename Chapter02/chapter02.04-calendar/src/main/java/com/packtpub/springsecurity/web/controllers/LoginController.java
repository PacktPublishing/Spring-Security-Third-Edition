package com.packtpub.springsecurity.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        logger.info("******login(error): {} ***************************************", error);
        logger.info("******login(logout): {} ***************************************", logout);

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("message", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    // Login form with error
//    @RequestMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        logger.info("******loginError() contains error? : {} ******************************************",
                model.containsAttribute("error"));
        return "login";
    }

    // Login form with error
//    @RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("error", true);
        logger.info("******logout() contains error? : {} ******************************************",
                model.containsAttribute("error"));
        return "login";
    }
}
