package ru.tbank.timedstarter.config.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.tbank.timedstarter.annotation.Timed;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TimedMethodAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> beanMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!bean.getClass().isAnnotationPresent(Timed.class)) {
            for (Method method : bean.getClass().getMethods()) {
                if (method.isAnnotationPresent(Timed.class)) {
                    beanMap.put(beanName, bean.getClass());
                    break;
                }
            }
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = beanMap.get(beanName);

        if (beanClass != null) {
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            proxyFactory.addAdvice((MethodInterceptor) invocation -> {
                Method method = invocation.getMethod();
                if (beanClass.getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Timed.class)) {
                    log.info("Method {} in class {} started", method.getName(), beanClass);
                    Instant methodStartTime = Instant.now();
                    Object result = invocation.proceed();
                    Instant methodEndTime = Instant.now();
                    log.info("Method {} in class {} finished with time: {} nanos",
                            method.getName(),
                            beanClass,
                            Duration.between(methodStartTime, methodEndTime).getNano());
                    return result;
                } else {
                    return invocation.proceed();
                }
            });

            return proxyFactory.getProxy();
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
