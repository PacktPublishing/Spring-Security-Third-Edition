package com.packtpub.springsecurity.web.access.intercept;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public final class RequestConfigMapping {

    private final RequestMatcher matcher;
    private final Collection<ConfigAttribute> attributes;

    public RequestConfigMapping(RequestMatcher matcher, ConfigAttribute attribute) {
        this(matcher, Collections.singleton(attribute));
    }

    public RequestConfigMapping(RequestMatcher matcher, Collection<ConfigAttribute> attributes) {
        if (matcher == null) {
            throw new IllegalArgumentException("matcher cannot be null");
        }
        Assert.notEmpty(attributes, "attributes cannot be null or emtpy");

        this.matcher = matcher;
        this.attributes = attributes;
    }

    public RequestMatcher getMatcher() {
        return matcher;
    }

    public Collection<ConfigAttribute> getAttributes() {
        return attributes;
    }

} // The End...
