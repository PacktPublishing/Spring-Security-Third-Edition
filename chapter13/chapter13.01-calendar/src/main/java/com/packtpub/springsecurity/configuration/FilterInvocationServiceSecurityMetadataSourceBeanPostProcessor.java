package com.packtpub.springsecurity.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.packtpub.springsecurity.web.access.intercept.FilterInvocationServiceSecurityMetadataSource;

@Component
public class FilterInvocationServiceSecurityMetadataSourceBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private FilterInvocationServiceSecurityMetadataSource metadataSource;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof FilterInvocationSecurityMetadataSource) {
            return metadataSource;
        }
        if(bean instanceof FilterChainProxy.FilterChainValidator) {
            return new FilterChainProxy.FilterChainValidator() {
                @Override
                public void validate(FilterChainProxy filterChainProxy) {

                }
            };
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
