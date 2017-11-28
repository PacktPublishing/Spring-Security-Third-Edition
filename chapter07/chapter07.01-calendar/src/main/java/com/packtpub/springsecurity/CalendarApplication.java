package com.packtpub.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webjars.RequireJS;

@SpringBootApplication(scanBasePackages={
        "com.packtpub.springsecurity",
        "com.packtpub.springsecurity.configuration",
        "com.packtpub.springsecurity.domain",
        "com.packtpub.springsecurity.service",
        "com.packtpub.springsecurity.web",
        "com.packtpub.springsecurity.web.configuration",
})
@EntityScan("com.packtpub.springsecurity.domain")
public class CalendarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CalendarApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CalendarApplication.class, args);
	}

    /**
     * This RequestMapping is not needed, when using:
     * .addResourceLocations(classpath:/META-INF/resources/webjars/").resourceChain(true)
     *
     * See: http://www.webjars.org/documentation#springmvc
     * @return WebJars JavaScript Mapping
     */
    @ResponseBody
    @RequestMapping(value = "/webjarsjs", produces = "application/javascript")
    public String webjarjs() {
        return RequireJS.getSetupJavaScript("/webjars/");
    }

}
