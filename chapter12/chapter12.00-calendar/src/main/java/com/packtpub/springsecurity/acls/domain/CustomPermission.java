package com.packtpub.springsecurity.acls.domain;


import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.model.Permission;

/**
 * Creates a custom implementation of {@link Permission} which is injected into {@link DefaultPermissionFactory} so we
 * can have a permission named ADMIN_READ.
 *
 * @author Rob Winch
 *
 */
public class CustomPermission extends BasePermission {

    public static final Permission ADMIN_READ = new CustomPermission(1 << 5, 'M'); // 32

    public CustomPermission(int mask, char code) {
        super(mask, code);
    }

    private static final long serialVersionUID = -7695655824830259000L;

} // The end...
