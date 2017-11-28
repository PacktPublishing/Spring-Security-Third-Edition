package com.packtpub.springsecurity.web.authentication.rememberme;

import com.packtpub.springsecurity.domain.PersistentLogin;
import com.packtpub.springsecurity.repository.RememberMeTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import java.util.Date;

/**
 * <p>
 * A {@link Runnable} which can be used to clean expired persistent remember me tokens generated from
 * {@link JdbcTokenRepositoryImpl}. Note that only a single cleanup is done in {@link #run()} so consumers must loop
 * over invoking the {@link #run()} method in order to perform multiple cleanups.
 * </p>
 * <p>
 * See the configuration file at <code>src/main/webapp/WEB-INF/spring/cleaner.xml</code> to learn how to hook in with <a
 * href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html">Spring's
 * scheduling abstraction</a>.
 * </p>
 *
 * @author Mick Knutson
 *
 */
public class JpaTokenRepositoryCleaner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RememberMeTokenRepository rememberMeTokenRepository;

    private final long tokenValidityInMs;

    /**
     *
     * @param rememberMeTokenRepository
     *            the {@link RememberMeTokenRepository} used to perform the cleanup. Cannot be null.
     * @param tokenValidityInMs
     *            used to calculate when a token is expired. If the {@link #run()} method is invoked, tokens older than
     *            this amount of time will be deleted. Cannot be less than 1.
     */
    public JpaTokenRepositoryCleaner(RememberMeTokenRepository rememberMeTokenRepository,
                                     long tokenValidityInMs) {
        if (rememberMeTokenRepository == null) {
            throw new IllegalArgumentException("jdbcOperations cannot be null");
        }
        if (tokenValidityInMs < 1) {
            throw new IllegalArgumentException("tokenValidityInMs must be greater than 0. Got " + tokenValidityInMs);
        }
        this.rememberMeTokenRepository = rememberMeTokenRepository;
        this.tokenValidityInMs = tokenValidityInMs;
    }

    public void run() {
        long expiredInMs = System.currentTimeMillis() - tokenValidityInMs;

        logger.info("Searching for persistent logins older than {}ms", tokenValidityInMs);

        try {
            Iterable<PersistentLogin> expired = rememberMeTokenRepository.findByLastUsedAfter(new Date(expiredInMs));
            for(PersistentLogin pl: expired){
                logger.info("*** Removing persistent login for {} ***", pl.getUsername());
                rememberMeTokenRepository.delete(pl);
            }
        } catch(Throwable t) {
            logger.error("**** Could not clean up expired persistent remember me tokens. ***", t);
        }
    }
} // The End...
