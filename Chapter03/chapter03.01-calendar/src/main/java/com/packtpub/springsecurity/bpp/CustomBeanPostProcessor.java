package com.packtpub.springsecurity.bpp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * CustomBeanPostProcessor
 * http://stackoverflow.com/questions/1201726/tracking-down-cause-of-springs-not-eligible-for-auto-proxying/19688634#19688634
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory
            .getLogger(CustomBeanPostProcessor.class);

    public CustomBeanPostProcessor() {
        logger.trace("*******************************************************");
        logger.trace("*******************************************************");
        logger.trace("0. Spring calls constructor");
        logger.trace("*******************************************************");
        logger.trace("*******************************************************");
    }

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(final Object bean,
                                                  final String beanName)
            throws BeansException {
        logger.trace("*******************************************************");
        logger.trace(bean.getClass() + "  " + beanName);
        return bean;
    }

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(final Object bean,
                                                 final String beanName)
            throws BeansException {
        logger.trace("*******************************************************");
        logger.trace(bean.getClass() + "  " + beanName);

        return bean;
    }
} // The End...
