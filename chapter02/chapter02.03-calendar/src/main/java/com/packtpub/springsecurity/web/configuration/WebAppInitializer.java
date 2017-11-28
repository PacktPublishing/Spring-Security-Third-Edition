package com.packtpub.springsecurity.web.configuration;

import com.packtpub.springsecurity.configuration.JavaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Replaces web.xml.txt in Servlet v.3.0+
 *
 * @see
 */
@Order(1)
public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JavaConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // or *.html
    }

    @Override
    public void onStartup(final ServletContext servletContext)
            throws ServletException {

        // Register DispatcherServlet
        super.onStartup(servletContext);

        // Register H2 Admin console:
        ServletRegistration.Dynamic h2WebServlet = servletContext.addServlet("h2WebServlet",
                "org.h2.server.web.WebServlet");
        h2WebServlet.addMapping("/admin/h2/*");
        h2WebServlet.setInitParameter("webAllowOthers", "true");

    }

} // The End...
