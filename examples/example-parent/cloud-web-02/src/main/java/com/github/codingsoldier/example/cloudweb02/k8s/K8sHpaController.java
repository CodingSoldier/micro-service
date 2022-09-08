package com.github.codingsoldier.example.cloudweb02.k8s;


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
            MemObj memObj = new MemObj(UUID.randomUUID().toString(), n, new byte[n]);
            map.put(UUID.randomUUID().toString(), memObj);
            log.info("########mem###{}", i);
        }
        return "ok";
    }

    @RequestMapping("/packets-per-second")
    public String packetsPerSecond(@RequestParam("num") Integer num) {
        log.info("###########packetsPerSecond#######{}", num);
        return "ok";
    }

}
