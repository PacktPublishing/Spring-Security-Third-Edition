package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.*;

@Configuration
@Import({SecurityConfig.class, DataSourceConfig.class})
@ComponentScan(basePackages = {
                "com.packtpub.springsecurity",
//                "com.packtpub.springsecurity.domain",
//                "com.packtpub.springsecurity.service"
}, excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.packtpub.springsecurity.web.*")
})
public class JavaConfig {

} // The end...
