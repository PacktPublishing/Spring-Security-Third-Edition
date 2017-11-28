package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.*;

@Configuration
@Import({SecurityConfig.class, DataSourceConfig.class})
public class JavaConfig {

} // The end...
