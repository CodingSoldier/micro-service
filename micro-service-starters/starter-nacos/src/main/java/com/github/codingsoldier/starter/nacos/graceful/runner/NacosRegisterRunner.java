package com.github.codingsoldier.starter.nacos.graceful.runner;

import com.github.codingsoldier.starter.nacos.graceful.config.NacosGraceful;
import com.github.codingsoldier.starter.nacos.graceful.properties.NacosGracefulProperties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author chenpq05
 * @since 2023/11/13 11:06
 */
@Slf4j
@Component
public class NacosRegisterRunner implements CommandLineRunner {

  @Autowired
  private NacosGraceful nacosGraceful;
  @Autowired
  private NacosGracefulProperties nacosGracefulProperties;
  @Autowired
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  /**
   * 手动上线
   */
  @Override
  public void run(String... args) {

    threadPoolTaskExecutor.execute(() -> {
      try {
        long delayRegisterMilliseconds = nacosGracefulProperties.getDelayRegisterMilliseconds();
        if (delayRegisterMilliseconds > 0) {
          TimeUnit.MILLISECONDS.sleep(delayRegisterMilliseconds);
        }
        // nacos客户端上线
        nacosGraceful.registerInstance();
      }catch (InterruptedException e) {
        log.error("nacos客户端实例上线前发生中断异常", e);
        Thread.currentThread().interrupt();
      }catch (Exception e) {
        log.error("nacos客户端实例上线失败", e);
      }
    });
  }

}
