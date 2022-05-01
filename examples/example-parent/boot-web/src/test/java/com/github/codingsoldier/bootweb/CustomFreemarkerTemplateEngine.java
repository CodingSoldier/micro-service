/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.codingsoldier.bootweb;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 *
 * @author nieqiurong
 * @since 2018-01-11
 */
public class CustomFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFreemarkerTemplateEngine.class);

    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String otherPath = getPathInfo(OutputFile.other);
        customFile.forEach((key, value) -> {
            System.out.println("@@@ key=" + key);
            System.out.println("@@@ value=" + value);
            String path = otherPath;
            if (value.endsWith("Dto.java.ftl")){
                // dto的输出目录
                path = path + File.separator + "dto";
            } else if (value.endsWith("Vo.java.ftl")) {
                // vo的输出目录
                path = path + File.separator + "vo";
            } else if (value.endsWith("Ao.java.ftl")) {
                // ao的输出目录
                path = path + File.separator + "ao";
            }
            // 目录 + 文件名
            String fileName = String.format((path + File.separator + "%s%s"), key, ".java");
            // 生成java文件
            outputFile(new File(fileName), objectMap, value);
        });
    }

}
