package com.github.codingsoldier.starter.web.notice.dingtalk;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Description 企业微信webhook配置类
 * @Date 2021/12/9 10:12
 * @Author chenpq
 */
@ConfigurationProperties(prefix = "framework.starter.web.ding-talk")
public class DingTalkProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否启用钉钉机器人
   */
  private boolean enable = false;

  /**
     * 信息最大长度
   */
  private Integer contentMaxLength = 5000;

  /**
   * 标题
   */
  private String title = "标题";

  /**
   * 加签
   */
  private String secret;

  /**
   * access_token
   */
  private String accessToken;



  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
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

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
