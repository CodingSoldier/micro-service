package com.github.codingsoldier.starter.web.notice.workweixin;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description 企业微信webhook配置类
 * @Date 2021/12/9 10:12
 * @Author chenpq
 */
@ConfigurationProperties(prefix = "micro-service.starter.web.work-weixin")
public class WorkWeiXinProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否启用企业微信
   */
  private boolean enable = false;

  /**
   * 信息最大长度
   */
  private Integer contentMaxLength = 1200;

  /**
   * 企业微信机器人webhook-url key
   */
  private String key;

  /**
   * 标题
   */
  private String title = "标题";

  /**
   * 标题颜色（只支持3种内置颜色） info 绿色 comment 灰色 warning 橙红色
   */
  private String titleColor = "";

  public WorkWeiXinProperties() {
    // sonar检测
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Integer getContentMaxLength() {
    return contentMaxLength;
  }

  public void setContentMaxLength(Integer contentMaxLength) {
    this.contentMaxLength = contentMaxLength;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitleColor() {
    return titleColor;
  }

  public void setTitleColor(String titleColor) {
    this.titleColor = titleColor;
  }

}
