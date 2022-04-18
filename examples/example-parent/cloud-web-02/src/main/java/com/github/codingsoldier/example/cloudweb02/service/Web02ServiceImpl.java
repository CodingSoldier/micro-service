package com.github.codingsoldier.example.cloudweb02.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Web02ServiceImpl implements Web02Service{

    @Override
    public String getTestVal(String name){
        log.info("############{}", name);
        return "service";
    }


}