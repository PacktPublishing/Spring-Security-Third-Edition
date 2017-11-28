package com.packtpub.springsecurity.web.authentication.rememberme;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

/**
 * <p>
 * A {@link Runnable} which can be used to clean expired persistent remember me tokens generated from
 * {@link JdbcTokenRepositoryImpl}. Note that only a single cleanup is done in {@link #run()} so consumers must loop
 * over invoking the {@link #run()} method in order to perform multiple cleanups.
 * </p>
 * <p>
 * See the configuration file at <code>src/main/webapp/WEB-INF/spring/cleaner.xml</code> to learn how to hook in with <a
 * href="http://static.springsource.org/spring/docs/3.1.x/spring-framework-reference/html/scheduling.html">Spring's
 * scheduling abstraction</a>.
 * </p>
 *
 * @author Rob Winch
 *
 */
public final class JdbcTokenRepositoryImplCleaner implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcOperations jdbcOperations;
    private final long tokenValidityInMs;

    /**
     *
     * @param jdbcOperations
     *            the {@link JdbcOperations} used to perform the cleanup. Cannot be null.
     * @param tokenValidityInMs
     *            used to calculate when a token is expired. If the {@link #run()} method is invoked, tokens older than
     *            this amount of time will be deleted. Cannot be less than 1.
     */
    public JdbcTokenRepositoryImplCleaner(JdbcOperations jdbcOperations, long tokenValidityInMs) {
        if (jdbcOperations == null) {
            throw new IllegalArgumentException("jdbcOperations cannot be null");
        }
        if (tokenValidityInMs < 1) {
            throw new IllegalArgumentException("tokenValidityInMs must be greater than 0. Got " + tokenValidityInMs);
        }
        this.jdbcOperations = jdbcOperations;
        this.tokenValidityInMs = tokenValidityInMs;
    }

    public void run() {
        long expiredInMs = System.currentTimeMillis() - tokenValidityInMs;
        try {
            jdbcOperations.update("delete from persistent_logins where last_used <= ?", new Date(expiredInMs));
        }catch(Throwable t) {
            logger.error("Could not clean up expired persistent remember me tokens.",t);
        }
    }
}
