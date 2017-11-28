package com.packtpub.springsecurity.web.access.intercept;

import com.packtpub.springsecurity.repository.SecurityFilterMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.Comparator.*;

@Repository("requestConfigMappingService")
public class JpaRequestConfigMappingService implements RequestConfigMappingService {

    @Autowired
    private SecurityFilterMetadataRepository securityFilterMetadataRepository;

    @Autowired
    public JpaRequestConfigMappingService(
            final SecurityFilterMetadataRepository securityFilterMetadataRepository
    ) {
        if (securityFilterMetadataRepository == null) {
            throw new IllegalArgumentException("securityFilterMetadataRepository cannot be null");
        }
        this.securityFilterMetadataRepository = securityFilterMetadataRepository;
    }

    /**
     * .antMatchers("/admin/h2/**").permitAll()
     * .antMatchers("/").permitAll()
     * .antMatchers("/login/*").permitAll()
     * .antMatchers("/logout").permitAll()
     * .antMatchers("/signup/*").permitAll()
     * .antMatchers("/errors/**").permitAll()
     * .antMatchers("/admin/*").access("hasRole('ADMIN') and isFullyAuthenticated()")
     * .antMatchers("/events/").hasRole("ADMIN")
     * .antMatchers("/**").hasRole("USER");
     * @return
     */
    @Override
    public List<RequestConfigMapping> getRequestConfigMappings() {
        List<RequestConfigMapping> rcm = securityFilterMetadataRepository
                .findAll()
                .stream()
                .sorted((m1, m2) -> m1.getSortOrder() - m2.getSortOrder())
                .map(md -> {
                    return new RequestConfigMapping(
                                new AntPathRequestMatcher(md.getAntPattern()),
                                    new SecurityConfig(md.getExpression()));
        }).collect(toList());
        return rcm;
    }

} // The End...
