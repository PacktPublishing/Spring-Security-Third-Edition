package com.packtpub.springsecurity.web.configuration;

import com.packtpub.springsecurity.configuration.DataSourceConfig;
import com.packtpub.springsecurity.configuration.JavaConfig;
import com.packtpub.springsecurity.configuration.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Replaces web.xml in Servlet v.3.0+
 * This class replaces the web.xml in Servlet v3.0+
 * with a {@link javax.servlet.ServletContainerInitializer}, 
 * which is the preferred approach to Servlet v3.0+ initialization. 
 * 
 * Spring Mvc provides {@link WebApplicationInitializer} interface leverage this mechanism. 
 * In Spring Mvc the preferred approach is to extend 
 * {@link AbstractAnnotationConfigDispatcherServletInitializer}
 *
 * @see
 */
public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JavaConfig.class, SecurityConfig.class, DataSourceConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*" };
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
