package com.github.codingsoldier.starterweb.util;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;

/**
 * Excel工具类
 *
 * @author chenpq05
 * @since 2022/2/21 14:27
 */
@Slf4j
public class ExcelUtil {

  /**
   * 获取请求头
   *
   * @param response response
   * @param fileName 文件名
   * @throws IOException ex
   */
  public static HttpServletResponse getExcelResp(HttpServletResponse response, String fileName) throws IOException {
    fileName = StringUtils.isBlank(fileName) ? "文件" : fileName;

    // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    return response;
  }

  /**
   * 导出excel
   * https://www.yuque.com/easyexcel/doc/write#06e004ef-16
   *
   * @param response response
   * @param fileName 文件名
   * @param sheetName sheet名称
   * @param clazz 实体类
   * @param data 数据
   * @throws IOException ex
   */
  public static void download(HttpServletResponse response,
      String fileName, String sheetName,
      Class clazz, Collection data) throws IOException {
    sheetName = StringUtils.isBlank(sheetName) ? "Sheet1" : sheetName;
    HttpServletResponse excelResp = getExcelResp(response, fileName);
    EasyExcel.write(excelResp.getOutputStream(), clazz).sheet(sheetName).doWrite(data);
  }

  /**
   * 导出excel
   * @param fileName 文件名
   * @param sheetName sheet名称
   * @param clazz 实体类
   * @param data 数据
   */
  public static void downloadCatchException(String fileName, String sheetName,
      Class clazz, Collection data) {
    try {
      ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      HttpServletResponse response = servletRequestAttributes.getResponse();
      download(response, fileName, sheetName, clazz, data);
    } catch (Exception e) {
      log.error("导出Excel失败{}", e);
    }
  }

}
