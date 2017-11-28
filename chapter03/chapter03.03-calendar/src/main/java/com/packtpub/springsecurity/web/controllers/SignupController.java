package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import com.packtpub.springsecurity.web.model.SignupForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final Logger logger = LoggerFactory
            .getLogger(SignupController.class);

    private final UserContext userContext;
    private final CalendarService calendarService;

    @Autowired
    public SignupController(final UserContext userContext,
                            final CalendarService calendarService) {
        if (userContext == null) {
            throw new IllegalArgumentException("userContext cannot be null");
        }
        if (calendarService == null) {
            throw new IllegalArgumentException("calendarService cannot be null");
        }
        this.userContext = userContext;
        this.calendarService = calendarService;
    }

    @RequestMapping("/signup/form")
    public String signup(final @ModelAttribute SignupForm signupForm) {
        return "signup/form";
    }

    @RequestMapping(value="/signup/new", method=RequestMethod.POST)
    public String signup(final @Valid SignupForm signupForm,
                         final BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "signup/form";
        }

        String email = signupForm.getEmail();
        if(calendarService.findUserByEmail(email) != null) {
            result.rejectValue("email", "errors.signup.email", "Email address is already in use. FOO");
            redirectAttributes.addFlashAttribute("error", "Email address is already in use. FOO");
            return "signup/form";
        }

        CalendarUser user = new CalendarUser();
        user.setEmail(email);
        user.setFirstName(signupForm.getFirstName());
        user.setLastName(signupForm.getLastName());
        user.setPassword(signupForm.getPassword());

        int id = calendarService.createUser(user);
        user.setId(id);
        userContext.setCurrentUser(user);

        redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.");
        return "redirect:/";
    }
}
