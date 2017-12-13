package com.packtpub.springsecurity.web.controllers;

import javax.validation.Valid;

import com.packtpub.springsecurity.service.SpringSecurityUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import com.packtpub.springsecurity.web.model.SignupForm;

@Controller
public class SignupController {

    private static final Logger logger = LoggerFactory
            .getLogger(SignupController.class);

    private final UserContext userContext;
    private final CalendarService calendarService;

    @Autowired
    public SignupController(UserContext userContext, CalendarService calendarService) {
        if (userContext == null) {
            throw new IllegalArgumentException("userContext cannot be null");
        }
        if (calendarService == null) {
            throw new IllegalArgumentException("calendarService cannot be null");
        }
        this.userContext = userContext;
        this.calendarService = calendarService;
    }

    @GetMapping("/signup/form")
    public String signup(@ModelAttribute SignupForm signupForm) {
        return "signup/form";
    }

    @PostMapping(value="/signup/new")
    public String signup(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "signup/form";
        }

        String email = signupForm.getEmail();
        if(calendarService.findUserByEmail(email) != null) {
            result.rejectValue("email", "errors.signup.email", "Email address is already in use.");
            return "signup/form";
        }

        CalendarUser user = new CalendarUser();
        user.setEmail(email);
        user.setFirstName(signupForm.getFirstName());
        user.setLastName(signupForm.getLastName());
        user.setPassword(signupForm.getPassword());

        logger.info("CalendarUser: {}", user);

        int id = calendarService.createUser(user);
        user.setId(id);
        userContext.setCurrentUser(user);

        redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.");
        return "redirect:/";
    }

} // The End...
