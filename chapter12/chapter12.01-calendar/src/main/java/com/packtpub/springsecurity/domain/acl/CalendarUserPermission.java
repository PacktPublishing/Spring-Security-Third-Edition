package com.packtpub.springsecurity.domain.acl;

import com.packtpub.springsecurity.acls.domain.CustomPermission;
import org.springframework.security.acls.domain.AbstractPermission;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

public class CalendarUserPermission extends BasePermission { // was AbstractPermission

    public static final Permission ADMIN_READ = new CustomPermission(1 << 5, 'M'); // 32

    @Deprecated
    protected CalendarUserPermission(int mask) {
        super(mask);
        // TODO Auto-generated constructor stub
    }

    public CalendarUserPermission(int mask, char code) {
        super(mask, code);
    }

    private static final long serialVersionUID = -7695655824830259000L;

} // The end...
