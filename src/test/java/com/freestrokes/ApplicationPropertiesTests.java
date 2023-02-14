package com.freestrokes;

import com.freestrokes.config.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationPropertiesTests {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void contextLoads() {
        System.out.println(applicationProperties.getEndpoint());
        System.out.println(applicationProperties.getUser().getName());
        System.out.println(applicationProperties.getUser().getAge());
        System.out.println(applicationProperties.getUser().getGender());
        System.out.println(applicationProperties.getProperty().getTest().getList().toString());
    }

}
