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
package com.github.codingsoldier.starter.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 *
 * @author nieqiurong
 * @since 2018-01-11
 */

public class CustomFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFreemarkerTemplateEngine.class);

    private String dtoFtl = "Dto.java.ftl";
    private String voFtl = "Vo.java.ftl";

    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFiles, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String parentPath = getPathInfo(OutputFile.parent);
        customFiles.forEach(file -> {
            LOGGER.debug("file = {}", file);
            String filePath = parentPath;
            if (file.getTemplatePath().endsWith(dtoFtl)) {
                // dto的输出目录
                filePath = filePath + File.separator + "dto";
            } else if (file.getTemplatePath().endsWith(voFtl)) {
                filePath = filePath + File.separator + "vo";
            }
            String fileName = filePath + File.separator + file.getFileName() + ".java";
            outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
        });
    }

}
