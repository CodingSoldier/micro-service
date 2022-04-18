package com.github.codingsoldier.example.cloudweb02;


import com.github.codingsoldier.example.cloudweb02.controller.Web01Controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudWeb01ApplicationTests {

    @Autowired
    private Web01Controller web01Controller;

    @Test
    void contextLoads() {
        String sssss = web01Controller.testLocalFein("sssss");
        System.out.println(sssss);
    }

}
