package com.github.codingsoldier.bootweb.controller;


import com.github.codingsoldier.bootweb.dto.ExcelDemoDto;
import com.github.codingsoldier.starterweb.util.ExcelUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Api(tags = "Excel API")
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelTestController {

    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        ExcelUtil.downloadCatchException("文件名称", "", ExcelDemoDto.class, data());
    }

    private List<ExcelDemoDto> data() {
        List<ExcelDemoDto> list = new ArrayList<ExcelDemoDto>();
        for (int i = 0; i < 10; i++) {
            ExcelDemoDto data = new ExcelDemoDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

}
