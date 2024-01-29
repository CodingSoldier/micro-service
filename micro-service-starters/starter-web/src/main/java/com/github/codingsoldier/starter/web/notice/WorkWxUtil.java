package com.github.codingsoldier.starter.web.notice;

import com.github.codingsoldier.common.util.OkHttpUtil;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import com.github.codingsoldier.starter.web.properties.WorkWeiXinProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.util.HashMap;

/**
 * 企业微信webhook机器人 <a href="https://work.weixin.qq.com/api/doc/90000/90136/91770">...</a>
 * @author chenpq05
 * @since 2022/2/11 11:58
 */
@DependsOn("applicationContextHolder")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WorkWeiXinProperties.class)
@Slf4j
public class WorkWxUtil {

  private static WorkWeiXinProperties workWeiXinProperties;

  @SuppressWarnings("squid:S2696")
  @PostConstruct
  public void init() {
    WorkWxUtil.workWeiXinProperties = ApplicationContextHolder.getBean(WorkWeiXinProperties.class);
  }

  /**
   * 是否发送信息
   *
   * @param msg 信息
   * @return 是否发送信息
   */
  private static boolean canSendMsg(String msg) {
    return workWeiXinProperties != null
        && workWeiXinProperties.isEnable()
        && StringUtils.isNotBlank(workWeiXinProperties.getKey())
        && StringUtils.isNotBlank(msg);
  }

  /**
   * 发送企业微信消息
   *
   * @param msg 消息
   */
  private static void send(String msg, Callback callback) {
    if (!canSendMsg(msg)) {
      log.debug("不发送企业微信消息");
      return ;
    }
    String titleMsg = StringUtils.isBlank(workWeiXinProperties.getTitleColor())
        ? workWeiXinProperties.getTitle()
        : String.format("<font color=\"%s\">%s</font>", workWeiXinProperties.getTitleColor(),
            workWeiXinProperties.getTitle());

    String contentStr = String.format("# %s %n %s", titleMsg, msg);
    int contentMaxLength = workWeiXinProperties.getContentMaxLength() < 4000
            ? workWeiXinProperties.getContentMaxLength() : 4000;
    contentStr = StringUtils.substring(contentStr, 0, contentMaxLength);
    HashMap<String, String> content = new HashMap<>(16);
    content.put("content", contentStr);

    HashMap<String, Object> param = new HashMap<>(16);
    param.put("msgtype", "markdown");
    param.put("markdown", content);
    String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + workWeiXinProperties.getKey();
    OkHttpUtil.asynchronousPost(url, param, callback);
  }

  /**
   * 异步发送企业微信消息
   *
   * @param msg 消息
   */
  @SuppressWarnings("squid:S125")
  public static void sendAsynchronous(String msg) {
    try {
      send(msg, new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {
          log.error("", e);
        }

        @Override
        public void onResponse(Call call, Response response) {
          if (!response.isSuccessful()) {
            log.error("发送企业微信信息失败，response={}", response);
          } else {
            // String respBodyStr = response.body().string();
            // log.debug("发送企业微信信息返回结果，responseBodyStr={}", respBodyStr);
          }
        }
      });
    } catch (Exception e) {
      log.error("发送企业微信消息异常", e);
    }
  }

  /**
   * 异步发送异常到企业微信
   *
   * @param throwable 异常
   */
  public static void sendAsynchronous(Throwable throwable) {
    try {
      sendAsynchronous(ExceptionUtils.getStackTrace(throwable));
    } catch (Exception ex) {
      log.error("", ex);
    }
  }

}
