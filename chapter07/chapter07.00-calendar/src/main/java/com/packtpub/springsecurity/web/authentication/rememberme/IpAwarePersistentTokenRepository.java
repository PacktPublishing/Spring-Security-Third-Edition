package com.packtpub.springsecurity.web.authentication.rememberme;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * A {@link PersistentTokenRepository} that looks up and stores the {@link PersistentRememberMeToken} based upon the
 * seriesId + the current user's IP Address. This means that in order to use the token, the user must have the same IP
 * address.
 * </p>
 * <p>
 * The implementation relies on Spring's {@link RequestContextHolder} to populated. In our application, we choose to
 * enable this by adding the in our web.xml as shown below. The dependency on spring-web is not an issue since
 * spring-security-web already depends on spring-web and we are implementing an interface in spring-security-web.
 * </p>
 *
 * <p>
 * A snippet of src/main/webapp/WEB-INF/web.xml
 * </p>
 *
 * <pre>
 *  &lt;listener&gt;
 *      &lt;listener-class>org.springframework.web.context.request.RequestContextListener&lt;/listener-class&gt;
 *  &lt;/listener&gt;
 * </pre>
 *
 * @author Rob Winch
 * @see RequestContextHolder
 */
public final class IpAwarePersistentTokenRepository implements PersistentTokenRepository {
    private final PersistentTokenRepository delegateRepository;

    /**
     * Creates a new {@link IpAwarePersistentTokenRepository} that after converting the seriesId to contain the current
     * user's IP address will use the delegateRepository to do all the work.
     *
     * @param delegateRepository
     */
    public IpAwarePersistentTokenRepository(PersistentTokenRepository delegateRepository) {
        if (delegateRepository == null) {
            throw new IllegalArgumentException("delegateRepository cannot be null");
        }
        this.delegateRepository = delegateRepository;
    }

    /**
     * Stores the new {@link PersistentRememberMeToken} but first changes the series to be series + currentIpAddress and
     * then delegates to the injected {@link PersistentTokenRepository} to do all the work.
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String ipSeries = ipSeries(token.getSeries());
        PersistentRememberMeToken ipToken = tokenWithSeries(token, ipSeries);
        this.delegateRepository.createNewToken(ipToken);
    }

    /**
     * Updates the token with the id series + currentIpAddress and then delegates to the injected
     * {@link PersistentTokenRepository} to do all the work.
     */
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String ipSeries = ipSeries(series);
        this.delegateRepository.updateToken(ipSeries, tokenValue, lastUsed);
    }

    /**
     * Gets the {@link PersistentRememberMeToken} for the given seriesId + currentIpAddress. By always adding the IP
     * address to the identifier we guarantee that the token can only be retrieved if the IP of the original token
     * matches the current user's token. It then delegates to the injected {@link PersistentTokenRepository} to do all
     * the work.
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String ipSeries = ipSeries(seriesId);
        PersistentRememberMeToken ipToken = delegateRepository.getTokenForSeries(ipSeries);
        return tokenWithSeries(ipToken, seriesId);
    }

    /**
     * Delegate to the injected {@link PersistentTokenRepository} to do all the work.
     */
    @Override
    public void removeUserTokens(String username) {
        delegateRepository.removeUserTokens(username);
    }

    /**
     * Creates a new {@link PersistentRememberMeToken} with a new series value or null of the token is null.
     *
     * @param token
     * @param series
     * @return
     */
    private PersistentRememberMeToken tokenWithSeries(PersistentRememberMeToken token, String series) {
        if (token == null) {
            return null;
        }
        return new PersistentRememberMeToken(token.getUsername(), series, token.getTokenValue(), token.getDate());
    }

    /**
     * Given a series will concatenate the current user's IP address with the series.
     *
     * @param series
     * @return
     */
    private String ipSeries(String series) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("RequestContextHolder.getRequestAttributes() cannot be null");
        }
        return series + attributes.getRequest().getRemoteAddr();
    }
}