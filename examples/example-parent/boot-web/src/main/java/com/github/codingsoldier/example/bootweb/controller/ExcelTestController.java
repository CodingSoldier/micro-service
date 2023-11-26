package com.github.codingsoldier.example.bootweb.controller;


import com.github.codingsoldier.example.bootweb.dto.ExcelDemoDTO;
import com.github.codingsoldier.starter.web.util.ExcelUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Tag(name = "Excel-API")
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelTestController {

    @GetMapping("/download")
    public void download() {
        ExcelUtil.downloadCatchException("文件名称", "", ExcelDemoDTO.class, data());
    }

    private List<ExcelDemoDTO> data() {
        List<ExcelDemoDTO> list = new ArrayList<ExcelDemoDTO>();
        for (int i = 0; i < 10; i++) {
            ExcelDemoDTO data = new ExcelDemoDTO();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

}
