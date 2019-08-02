package com.dnvriend.starter;

import io.vavr.control.Option;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.val;

@Configuration
@ConditionalOnClass(Greeter.class)
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {
    private final GreeterProperties greeterProperties;

    public GreeterAutoConfiguration(GreeterProperties greeterProperties) {
        this.greeterProperties = greeterProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public GreetingConfig greeterConfig() {
        val userName = Option.of(greeterProperties.getUserName())
                .getOrElse(System.getProperty("user.name"));
        val morningMessage = Option.of(greeterProperties.getMorningMessage())
                .getOrElse("Hello %s, it is morning");
        val afternoonMessage = Option.of(greeterProperties.getAfternoonMessage())
                .getOrElse("Hello %s, it is afternoon");
        val eveningMessage = Option.of(greeterProperties.getEveningMessage())
                .getOrElse("Hello %s, it is evening");
        val nightMessage = Option.of(greeterProperties.getNightMessage())
                .getOrElse("Hello %s, it is night");

        return GreetingConfig.builder()
                .userName(userName)
                .morningMessage(morningMessage)
                .afternoonMessage(afternoonMessage)
                .eveningMessage(eveningMessage)
                .nightMessage(nightMessage)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public Greeter greeter(GreetingConfig greetingConfig) {
        return new Greeter(greetingConfig);
    }
}
