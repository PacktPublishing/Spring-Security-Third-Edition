package com.packtpub.springsecurity.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;

/**
 * This displays the welcome screen that shows what will be happening in this chapter.
 *
 * @author Rob Winch
 *
 */
@Controller
public class WelcomeController {

    private static final Logger logger = LoggerFactory
            .getLogger(WelcomeController.class);

    private final WebInvocationPrivilegeEvaluator webInvocationPriviledgeEvaluator;

    @Autowired
    public WelcomeController(WebInvocationPrivilegeEvaluator webPrivEvaluator) {
        this.webInvocationPriviledgeEvaluator = webPrivEvaluator;
    }

    /**
     * Populates a {@link HttpServletRequest} attribute named usernameContainsUser for any URL processed by this
     * controller. The result is based upon if the username contains "user".
     *
     * @param authentication
     *            Contains the current {@link Authentication} object. This is a more simple way of obtaining the
     *            Authentication from {@link SecurityContextHolder#getContext()}.
     * @return
     */
    @ModelAttribute("showCreateLink")
    public boolean showCreateLink(Authentication authentication) {
        // NOTE We could also get the Authentication from SecurityContextHolder.getContext().getAuthentication()
        return authentication != null && authentication.getName().contains("user");
    }

    /**
     * Populates a {@link HttpServletRequest} attribute named showAdminLink for any URL processed by this controller.
     * The result is based upon if the user has access to the URL /admin/. This demonstrates if you are not using JSP
     * tags how you can leverage the http.authorizeRequests() mappings.
     *
     * @param authentication
     *            Contains the current {@link Authentication} object. This is a more simple way of obtaining the
     *            Authentication from {@link SecurityContextHolder#getContext()}.
     * @return
     */
    @ModelAttribute("showAdminLink")
    public boolean showAdminLink(Authentication authentication) {
        // NOTE We could also get the Authentication from SecurityContextHolder.getContext().getAuthentication()
        return webInvocationPriviledgeEvaluator.isAllowed("/admin/", authentication);
    }

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

} // The End...
