package com.freestrokes;

import com.freestrokes.controller.BoardController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationContextTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        BoardController boardControllerBean = applicationContext.getBean(BoardController.class);
        assertThat(boardControllerBean).isNotNull();
    }

}
