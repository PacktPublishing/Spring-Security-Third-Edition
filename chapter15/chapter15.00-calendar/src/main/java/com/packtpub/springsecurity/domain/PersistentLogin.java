package com.packtpub.springsecurity.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity to map to an entry in the persistent_logins table.
 *
 * @author Mick Knutson
 *
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {


    @Id
    private String series;

    private String username;
    private String token;
    private Date lastUsed;

    public PersistentLogin(){}

    public PersistentLogin(PersistentRememberMeToken token){
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.token = token.getTokenValue();
        this.lastUsed = token.getDate();
    }


    public String getSeries() {
        return series;
    }
    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }
    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }


    // --- override Object --------------------------------------------------//

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    private static final long serialVersionUID = 8433999509932007961L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

} // The End...
