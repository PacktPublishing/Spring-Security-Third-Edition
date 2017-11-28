package com.packtpub.springsecurity.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages =
        {
                "com.packtpub.springsecurity.dataaccess",
                "com.packtpub.springsecurity.domain",
                "com.packtpub.springsecurity.service"
        }
)
@PropertySource(value = {"classpath:application.properties"})
public class JavaConfig {

    /**
     * Note: If you want to use @PropertySource, you must create a static
     * PropertySourcesPlaceholderConfigurer with the @Bean as seen here.
     * @return PropertySourcesPlaceholderConfigurer
     * @throws java.io.IOException
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
        propertySourcesPlaceholderConfigurer.setProperties(yamlPropertiesFactoryBean().getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public static YamlPropertiesFactoryBean yamlPropertiesFactoryBean() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        return yaml;
    }
} // The end...
