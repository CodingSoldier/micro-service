package com.github.codingsoldier.example.cloudweb02.k8s;


import com.github.codingsoldier.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/k8s-hpa")
public class K8sHpaController {

    public Map map = new HashMap();

    @RequestMapping("/mem")
    public String mem(@RequestParam("num") Integer num) {
        for (int i = 0; i < num; i++) {
            int n = new Random().nextInt(Integer.MAX_VALUE);
            MemObj memObj = new MemObj(UUID.randomUUID().toString(), n, new byte[num*1024*1024]);
            map.put(UUID.randomUUID().toString(), memObj);
            log.info("########mem###{}", i);
        }
        return "ok";
    }

    @RequestMapping("/cpu")
    public String cpu(@RequestParam("num") Integer num) {
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                while (true) {
                    log.info("########mem###{}", CommonUtil.uuid32());
                }
            }).start();
        }
        return "ok";
    }

    @RequestMapping("/requests-per-second")
    public String requestsPerSecond(@RequestParam("num") Integer num) {
        log.info("#00requ-stsPer###{}", UUID.randomUUID().toString() + num);
        return UUID.randomUUID().toString();
    }
}
