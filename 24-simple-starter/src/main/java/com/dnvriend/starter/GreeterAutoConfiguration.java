package com.dnvriend.starter;

import static com.dnvriend.starter.GreeterConstants.AFTERNOON_MESSAGE;
import static com.dnvriend.starter.GreeterConstants.EVENING_MESSAGE;
import static com.dnvriend.starter.GreeterConstants.MORNING_MESSAGE;
import static com.dnvriend.starter.GreeterConstants.NIGHT_MESSAGE;

import io.vavr.control.Option;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Greeter.class)
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {

    private final GreeterProperties greeterProperties;

    public GreeterAutoConfiguration(GreeterProperties greeterProperties) {
        this.greeterProperties = greeterProperties;
        System.out.println(greeterProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public GreetingConfig greeterConfig() {
        val userName = Option.of(greeterProperties.getUserName())
            .getOrElse(System.getProperty("user.name"));
        val morningMessage = Option.of(greeterProperties.getMessages().getMorningMessage())
            .getOrElse(MORNING_MESSAGE);
        val afternoonMessage = Option.of(greeterProperties.getMessages().getAfternoonMessage())
            .getOrElse(AFTERNOON_MESSAGE);
        val eveningMessage = Option.of(greeterProperties.getMessages().getEveningMessage())
            .getOrElse(EVENING_MESSAGE);
        val nightMessage = Option.of(greeterProperties.getMessages().getNightMessage())
            .getOrElse(NIGHT_MESSAGE);

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
