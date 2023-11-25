package com.github.codingsoldier.starter.web.util;

import com.alibaba.excel.EasyExcelFactory;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * Excel工具类
 *
 * @author chenpq05
 * @since 2022/2/21 14:27
 */
@Slf4j
public class ExcelUtil {

    private ExcelUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * 获取请求头
     *
     * @param response response
     * @param fileName 文件名
     */
    public static HttpServletResponse getExcelResp(HttpServletResponse response, String fileName) {
        fileName = StringUtils.isBlank(fileName) ? "文件" : fileName;

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        return response;
    }

    /**
     * 导出excel
     * <a href="https://www.yuque.com/easyexcel/doc/write#06e004ef-16">...</a>
     *
     * @param response  response
     * @param fileName  文件名
     * @param sheetName sheet名称
     * @param clazz     实体类
     * @param data      数据
     * @throws IOException ex
     */
    public static void download(HttpServletResponse response,
                                String fileName, String sheetName,
                                Class<?> clazz, Collection<?> data) throws IOException {
        sheetName = StringUtils.isBlank(sheetName) ? "Sheet1" : sheetName;
        HttpServletResponse excelResp = getExcelResp(response, fileName);
        EasyExcelFactory.write(excelResp.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
    }

    /**
     * 导出excel
     *
     * @param fileName  文件名
     * @param sheetName sheet名称
     * @param clazz     实体类
     * @param data      数据
     */
    public static void downloadCatchException(String fileName, String sheetName,
                                              Class<?> clazz, Collection<?> data) {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (servletRequestAttributes != null) {
                HttpServletResponse response = servletRequestAttributes.getResponse();
                download(response, fileName, sheetName, clazz, data);
            } else {
                log.error("servletRequestAttributes为空");
            }
        } catch (Exception e) {
            log.error("导出Excel失败", e);
        }
    }

}
