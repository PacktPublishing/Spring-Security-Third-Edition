package com.packtpub.springsecurity.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Allows users to view the active sessions associated to their account and remove sessions that should no longer be active.
 *
 * @author Rob Winch
 *
 */
@Controller
public class UserSessionController {

    private final SessionRegistry sessionRegistry;


    @Autowired
    public UserSessionController(SessionRegistry sessionRegistry) {
        if (sessionRegistry == null) {
            throw new IllegalArgumentException("sessionRegistry cannot be null");
        }
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/user/sessions/")
    public String sessions(Authentication authentication, ModelMap model) {
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(),
                false);
        model.put("sessions", sessions);
        return "user/sessions";
    }

    @RequestMapping(value="/user/sessions/{sessionId}", method = RequestMethod.DELETE)
    public String removeSession(@PathVariable String sessionId, RedirectAttributes redirectAttrs) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if(sessionInformation != null) {
            sessionInformation.expireNow();
        }
        redirectAttrs.addFlashAttribute("message", "Session was removed");
        return "redirect:/user/sessions/";
    }
}
