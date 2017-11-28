package com.packtpub.springsecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory
            .getLogger(DataSourceConfig.class);

    private EmbeddedDatabase database = null;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Embedded H2 database
     * Connect to running database with the following details:
     *
     * URL: jdbc:h2:mem:dataSource
     * Driver Class: org.h2.Driver
     * Username: sa
     * Password: [blank]
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        database = builder.setType(EmbeddedDatabaseType.H2)
                .setName("dataSource")
                .ignoreFailedDrops(true)
                .continueOnError(false)
                .addScript("classpath:database/h2/calendar-schema.sql")
                .addScript("classpath:database/h2/calendar-data.sql")
                .build();
        return database;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcOperations(final DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }


    /**
     * Used for JSR-303 Validation
     * TODO: look into JSR-349 Validation
     */
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }


    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        final MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
//        methodValidationPostProcessor.setValidator(validatorFactoryBean());

        return methodValidationPostProcessor;
    }



    /**
     * DataSource PostConstruct call-back
     * @throws SQLException
     */
    @PostConstruct
    public void dataSourceInitialization() {

        // h2 admin via hsql Database Manager
//        DatabaseManagerSwing.main(new String[] { "--url", "jdbc:h2:mem:dataSource", "--user", "sa", "--password", "" });
    }

    /**
     * DataSource PreDestroy call-back
     * @throws SQLException
     */
    @PreDestroy()
    public void dataSourceDestroy() throws SQLException {
        if (database != null) {
            database.shutdown();
        }
    }

} // The End...
