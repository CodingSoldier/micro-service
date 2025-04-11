package com.github.codingsoldier.example.bootweb.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.Data;

@Data
public class ExcelDemoDTO {

  @ExcelProperty("字符串标题")
  private String string;

  @ExcelProperty("日期标题")
  private Date date;

  @ExcelProperty("数字标题")
  private Double doubleData;

  /**
   * 忽略这个字段
   */
  @ExcelIgnore
  private String ignore;

}
