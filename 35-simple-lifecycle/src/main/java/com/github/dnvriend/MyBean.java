package com.github.dnvriend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Slf4j
@Component
public class MyBean implements
    ApplicationContextAware,
    ApplicationEventPublisherAware,
    BeanClassLoaderAware,
    BeanFactoryAware,
    BeanNameAware,
    LoadTimeWeaverAware,
    MessageSourceAware,
    NotificationPublisherAware,
    ResourceLoaderAware,
    ImportAware,
    EnvironmentAware,
    EmbeddedValueResolverAware,
    ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("Received event: " + event.getClass().getSimpleName() + " details: " + event.toString());
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.debug("setBeanClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.debug("setBeanFactory");

    }

    @Override
    public void setBeanName(String name) {
        log.debug("setBeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("setApplicationContext");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.debug("setApplicationEventPublisher");
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        log.debug("setEmbeddedValueResolver");

    }

    @Override
    public void setEnvironment(Environment environment) {
        log.debug("setEnvironment");

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        log.debug("setMessageSource");

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.debug("setResourceLoader");

    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        log.debug("setImportMetadata");

    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        log.debug("setLoadTimeWeaver");

    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        log.debug("setNotificationPublisher");

    }
}
