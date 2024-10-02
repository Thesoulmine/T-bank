package ru.tbank.timedstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tbank.timedstarter.config.beanpostprocessor.TimedMethodAnnotationBeanPostProcessor;
import ru.tbank.timedstarter.config.beanpostprocessor.TimedTypeAnnotationBeanPostProcessor;

@ConditionalOnProperty(name = {"timed.enabled"}, havingValue = "true")
@Configuration
public class TimedAnnotationBeanPostProcessorAutoConfiguration {

    @ConfigurationProperties("timed")
    @Bean
    public TimedConfigProperties timedConfigProperties() {
        return new TimedConfigProperties();
    }

    @Bean
    public TimedMethodAnnotationBeanPostProcessor timedMethodAnnotationBeanPostProcessor() {
        return new TimedMethodAnnotationBeanPostProcessor();
    }

    @Bean
    public TimedTypeAnnotationBeanPostProcessor timedTypeAnnotationBeanPostProcessor() {
        return new TimedTypeAnnotationBeanPostProcessor();
    }
}
