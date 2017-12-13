package com.packtpub.springsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages={
        "com.packtpub.springsecurity",
        "com.packtpub.springsecurity.configuration",
        "com.packtpub.springsecurity.domain",
        "com.packtpub.springsecurity.service",
        "com.packtpub.springsecurity.web",
        "com.packtpub.springsecurity.web.configuration",
}
)
@EntityScan("com.packtpub.springsecurity.domain")
public class CalendarApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(CalendarApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CalendarApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CalendarApplication.class, args);
	}

	//TODO: Find out how to use debug=true property to control this:
//    @Profile("trace")
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            String[] beanNames = ctx.getBeanDefinitionNames();

            System.out.println("********************************************");
            // TODO: FIXME: Logger level not working
            logger.debug("**************************************************");
            System.out.println("Listing the "+beanNames.length+" beans loaded by Spring Boot:");
//            logger.debug("Listing the {} beans loaded by Spring Boot:", beanNames.length);

            Arrays.stream(beanNames)
                    .sorted().forEach(System.out::println);

            logger.debug("*** the End **************************************\n\n");
            System.out.println("*** the End ********************************\n\n");
        };

    }

}
