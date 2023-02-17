package com.freestrokes.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Data
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String endpoint;
    private User user;
    private Property property;

    @Getter
    @Setter
    public static class User {
        private String name;
        private String age;
        private String gender;
    }

    @Getter
    @Setter
    public static class Property {
        private Test test;
    }

    @Getter
    @Setter
    public static class Test {
        private List<String> list;
    }

}

