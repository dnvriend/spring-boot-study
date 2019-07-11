package com.github.dnvriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my.props")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProperties {
    String name;
    int age;
}