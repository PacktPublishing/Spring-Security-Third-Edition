package com.packtpub.springsecurity.ldap.userdetails.ad;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapRdn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An {@link LdapAuthoritiesPopulator} that is based on the {@link ActiveDirectoryLdapAuthenticationProvider}. The
 * implementation obtains the {@link GrantedAuthority}'s from the userData's memberOf attribute. It then uses the last
 * {@link LdapRdn}'s value as the {@link GrantedAuthority}.
 *
 * @author Rob Winch
 * @see ActiveDirectoryLdapAuthenticationProvider
 */
public final class ActiveDirectoryLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        String[] groups = userData.getStringAttributes("memberOf");
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String group : groups) {
            LdapRdn authority = new DistinguishedName(group).removeLast();
            authorities.add(new SimpleGrantedAuthority(authority.getValue()));
        }
        return authorities;
    }
}