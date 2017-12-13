package com.packtpub.springsecurity.configuration;

import com.packtpub.springsecurity.acls.domain.CustomPermission;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.PermissionCacheOptimizer;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

/**
 * Spring Security Config Class
 * @see {@link WebSecurityConfigurerAdapter}
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclConfig //extends GlobalMethodSecurityConfiguration
{

    private static final Logger logger = LoggerFactory
            .getLogger(AclConfig.class);


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;


    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler(){
        DefaultMethodSecurityExpressionHandler dmseh = new DefaultMethodSecurityExpressionHandler();

        dmseh.setPermissionEvaluator(permissionEvaluator());
        dmseh.setPermissionCacheOptimizer(permissionCacheOptimizer());
        return dmseh;
    }

    @Bean
    public AclPermissionCacheOptimizer permissionCacheOptimizer(){
        return new AclPermissionCacheOptimizer(aclService());
    }

    @Bean
    public AclPermissionEvaluator permissionEvaluator(){
        return new AclPermissionEvaluator(aclService());
    }

    @Bean
    public JdbcMutableAclService aclService(){
        return new JdbcMutableAclService(dataSource,
                                         lookupStrategy(),
                                         aclCache());
    }

    @Bean
    public LookupStrategy lookupStrategy(){
        return new BasicLookupStrategy(
                dataSource,
                aclCache(),
                aclAuthorizationStrategy(),
                consoleAuditLogger());
    }

    @Bean
    public ConsoleAuditLogger consoleAuditLogger(){
        return new ConsoleAuditLogger();
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"),
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"),
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")
        );
    }


    //--- EHCache Configuration ---------------------------------------------//
    @Bean
    public EhCacheBasedAclCache aclCache(){
        return new EhCacheBasedAclCache(ehcache(),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy()
                );
    }


    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy(){
        return new DefaultPermissionGrantingStrategy(consoleAuditLogger());
    }

    @Bean
    public Ehcache ehcache(){
        EhCacheFactoryBean cacheFactoryBean = new EhCacheFactoryBean();
        cacheFactoryBean.setCacheManager(cacheManager());
        cacheFactoryBean.setCacheName("aclCache");
        cacheFactoryBean.setMaxBytesLocalHeap("1M");
        cacheFactoryBean.setMaxEntriesLocalHeap(0L);
        cacheFactoryBean.afterPropertiesSet();
        return cacheFactoryBean.getObject();
    }

    @Bean
    public CacheManager cacheManager(){
        EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
        cacheManager.setAcceptExisting(true);
        cacheManager.setCacheManagerName(CacheManager.getInstance().getName());
        cacheManager.afterPropertiesSet();
        return cacheManager.getObject();
    }

    /**
     * Custom Permissions
     */
    @Bean
    public DefaultPermissionFactory permissionFactory(){
        return new DefaultPermissionFactory(CustomPermission.class);
    }

} // The End...
