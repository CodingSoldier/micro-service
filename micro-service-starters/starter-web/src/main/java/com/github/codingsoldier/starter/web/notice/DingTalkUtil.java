package com.github.codingsoldier.starter.web.notice;

import com.github.codingsoldier.common.util.OkHttpUtil;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import com.github.codingsoldier.starter.web.properties.DingTalkProperties;
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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;

@DependsOn("applicationContextHolder")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DingTalkProperties.class)
@Slf4j
public class DingTalkUtil {

    private static DingTalkProperties dingTalkProperties;

    @SuppressWarnings("squid:S2696")
    @PostConstruct
    public void init() {
        DingTalkUtil.dingTalkProperties = ApplicationContextHolder.getBean(DingTalkProperties.class);
    }

    /**
     * 是否发送信息
     *
     * @param msg 信息
     * @return 是否发送信息
     */
    private static boolean canSendMsg(String msg) {
        return dingTalkProperties != null
                && dingTalkProperties.isEnable()
                && StringUtils.isNotBlank(dingTalkProperties.getSecret())
                && StringUtils.isNotBlank(dingTalkProperties.getAccessToken())
                && StringUtils.isNotBlank(msg);
    }

    /**
     * 发送企业微信消息
     *
     * @param msg 消息
     */
    private static void send(String msg, Callback callback) throws Exception {
        if (!canSendMsg(msg)) {
            log.debug("不发送企业微信消息");
            return ;
        }
        //获取时间戳
        Long timestamp = System.currentTimeMillis();
        //定义密钥
        //把时间戳和密钥拼接成字符串，中间加入一个换行符
        String stringToSign = timestamp + "\n" + dingTalkProperties.getSecret();
        //声明一个Mac对象，用来操作字符串
        Mac mac = Mac.getInstance("HmacSHA256");
        //初始化Mac对象，设置Mac对象操作的字符串是UTF-8类型，加密方式是SHA256
        mac.init(new SecretKeySpec(dingTalkProperties.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        //把字符串转化成字节形式
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        //新建一个Base64编码对象
        Base64.Encoder encoder = Base64.getEncoder();
        //把上面的字符串进行Base64加密后再进行URL编码
        String sign = URLEncoder.encode(encoder.encodeToString(signData), StandardCharsets.UTF_8);
        String url = "https://oapi.dingtalk.com/robot/send?access_token="+dingTalkProperties.getAccessToken()+"&sign="+sign+"&timestamp="+timestamp;
        HashMap<String, Object> data = new HashMap<>();
        data.put("msgtype", "text");
        data.put("sign", sign);
        HashMap<String, String> textContent = new HashMap<>();
        data.put("text", textContent);

        msg = "【" + dingTalkProperties.getTitle() + "】" + LocalDateTime.now().withNano(0).toString().replace("T", " ") +" \n" + msg;

        textContent.put("content", StringUtils.substring(msg, 0, dingTalkProperties.getContentMaxLength()));

        OkHttpUtil.asynchronousPost(url, data, callback);
    }

    /**
     * 异步发送钉钉消息
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
                        log.error("发送钉钉信息失败，response={}", response);
                    } else {
                        // String respBodyStr = response.body().string();
                        // log.debug("发送钉钉信息返回结果，responseBodyStr={}", respBodyStr);
                    }
                }
            });
        } catch (Exception e) {
            log.error("发送钉钉消息异常", e);
        }
    }

    /**
     * 异步发送异常到钉钉
     *
     * @param throwable 异常
     */
    public static void sendAsynchronous(Throwable throwable) {
        sendAsynchronous(ExceptionUtils.getStackTrace(throwable));
    }


}
