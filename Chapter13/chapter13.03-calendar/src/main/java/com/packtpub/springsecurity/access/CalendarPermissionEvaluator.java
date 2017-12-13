package com.packtpub.springsecurity.access;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.packtpub.springsecurity.dataaccess.EventDao;
import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.domain.Event;

public final class CalendarPermissionEvaluator implements PermissionEvaluator {
    private final EventDao eventDao;

    public CalendarPermissionEvaluator(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * This method encapsulates our logic of determining if the current user has access to an {@link Event}. If we were
     * securing other domain objects, the logic could be added to here as well.
     *
     * @param authentication The current Spring Security {@link Authentication} which is not specified in the SpEL since it is automatically passed in by Spring Security.
     * @param targetDomainObject the object that we are determining if access is granted to. In our case, this is an {@link Event} object.
     * @param permission The type of permission for the {@link Event}. This is typically a String (i.e. "read", "write", etc).
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if(targetDomainObject instanceof Event) {
            return hasPermission(authentication, (Event) targetDomainObject, permission);
        }
        return targetDomainObject == null;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        if(!Event.class.getName().equals(targetType)) {
            throw new IllegalArgumentException("targetType is not supported. Got "+targetType);
        }
        if(!(targetId instanceof Integer)) {
            throw new IllegalArgumentException("targetId type is not supported. Got "+targetType);
        }
        Event event = eventDao.getEvent((Integer)targetId);
        return hasPermission(authentication, event, permission);
    }

    private boolean hasPermission(Authentication authentication, Event event, Object permission) {
        if(event == null) {
            return true;
        }

        // Custom Role verification
        GrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
        if(authentication.getAuthorities().contains(adminRole)) {
            return true;
        }
        String currentUserEmail = authentication.getName();
        String ownerEmail = extractEmail(event.getOwner());
        if("write".equals(permission)) {
            return currentUserEmail.equals(ownerEmail);
        } else if("read".equals(permission)) {
            String attendeeEmail = extractEmail(event.getAttendee());
            return currentUserEmail.equals(attendeeEmail) || currentUserEmail.equals(ownerEmail);
        }
        throw new IllegalArgumentException("permission "+permission+" is not supported.");
    }


    private String extractEmail(CalendarUser user) {
        if(user == null) {
            return null;
        }
        return user.getEmail();
    }

} // The End...
