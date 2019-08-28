package com.github.dnvriend.config;

import java.util.List;
import javax.servlet.Filter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties("com.dnvriend")
public class FilterProperties {
    private List<Class<? extends Filter>> filters;
}
