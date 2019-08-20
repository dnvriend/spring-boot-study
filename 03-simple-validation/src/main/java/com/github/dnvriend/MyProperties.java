package com.github.dnvriend;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my.props")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyProperties {

    @NotEmpty(message = "name should not be null")
    @Size(min = 4, max = 30, message = "name should be between 4 and 30 chars")
    private String name;

    @NotNull(message = "title should not be null")
    private String title;

    @Min(value = 0, message = "age should not be less than 0")
    @Max(value = 100, message = "age should not be greater than 100")
    private int age;

    @AssertTrue(message = "should be working")
    private boolean working;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be valid")
    private String email;
}