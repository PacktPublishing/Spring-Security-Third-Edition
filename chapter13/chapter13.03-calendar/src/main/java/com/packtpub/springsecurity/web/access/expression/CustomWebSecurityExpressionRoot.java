package com.packtpub.springsecurity.web.access.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

    public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
    }

    public boolean isLocal() {

//        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if(sra != null){
//            HttpServletRequest req = sra.getRequest();
//            if(req != null) {
//                String server = req.getServerName();
//                return "localhost".equals(server);
//            }
//        }
//        return "localhost".equals(request.getServerName());

        // Some SPeL enabled logic
        return true;
    }
}