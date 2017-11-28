package com.packtpub.springsecurity.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 *
 */
@Component
public class SocialDatabasePopulator implements InitializingBean {

    private final DataSource dataSource;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SocialDatabasePopulator(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource resource = new ClassPathResource(
                "org/springframework/social/connect/jdbc/JdbcUsersConnectionRepository.sql");
        executeSql(resource);
    }

    private void executeSql(final Resource resource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.addScript(resource);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

} // The end...
