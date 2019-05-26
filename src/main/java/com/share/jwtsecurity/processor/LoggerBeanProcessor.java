package com.share.jwtsecurity.processor;

import com.share.jwtsecurity.annotation.InjectLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class LoggerBeanProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerBeanProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        LOGGER.info("Processing bean post processor");

        List<Field> fields = Arrays.asList(bean.getClass().getDeclaredFields());

        fields.forEach(f -> {
            if (Logger.class.isAssignableFrom(f.getType()) && f.getAnnotation(InjectLogger.class) != null) {
                f.setAccessible(true);
                try {
                    f.set(bean, LoggerFactory.getLogger(bean.getClass()));
                } catch (Exception e) {
                    LOGGER.error("Could not assign loger for the bean {} - {}", bean.getClass(), e.getMessage());
                }
            }
        });
        return bean;
    }
}
