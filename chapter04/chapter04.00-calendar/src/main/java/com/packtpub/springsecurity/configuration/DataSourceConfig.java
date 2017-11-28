package com.packtpub.springsecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 * @author Mick Knutson
 * @since chapter 01.00
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Custom H2 implementation for our {@link EmbeddedDatabase}
     * @return
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setName("dataSource")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/database/h2/calendar-schema.sql")
                .addScript("/database/h2/calendar-data.sql")
                .addScript("/database/h2/security-schema.sql")
                .addScript("/database/h2/security-users.sql")
                .addScript("/database/h2/security-user-authorities.sql")
                .build();
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
    }

} // The End...
