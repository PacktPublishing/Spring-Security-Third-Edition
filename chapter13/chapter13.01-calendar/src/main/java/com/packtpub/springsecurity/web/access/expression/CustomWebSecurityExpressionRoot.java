package com.packtpub.springsecurity.web.access.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

    public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
    }

    public boolean isLocal() {
        return "localhost".equals(this.request.getServerName());
    }
}