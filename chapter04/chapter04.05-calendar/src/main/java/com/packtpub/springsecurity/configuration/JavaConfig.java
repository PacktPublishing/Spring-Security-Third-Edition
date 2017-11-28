package com.packtpub.springsecurity.configuration;

import org.springframework.context.annotation.*;

/**
 * @author Mick Knutson
 * @since chapter 01.00
 */
@Configuration
@Import({SecurityConfig.class, DataSourceConfig.class})
public class JavaConfig {

} // The end...
