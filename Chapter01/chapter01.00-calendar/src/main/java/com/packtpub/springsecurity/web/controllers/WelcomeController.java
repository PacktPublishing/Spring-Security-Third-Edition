package com.packtpub.springsecurity.web.controllers;

import com.packtpub.springsecurity.configuration.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

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

    @Autowired
    ApplicationContext context;


    @GetMapping(value="/")
    public String welcome() {
        String name = context.getMessage("customer.name",
                new Object[] { 46,"http://www.baselogic.com" }, Locale.US);

        System.out.println("Customer name (English) : " + name);
        logger.info("*** welcome(): {}", name);
        return "index";
    }
}