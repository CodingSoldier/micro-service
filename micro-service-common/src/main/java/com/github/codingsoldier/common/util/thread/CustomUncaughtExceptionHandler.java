package com.github.codingsoldier.common.util.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenpq05
 * @since 2023/2/17 15:40
 */
@Slf4j
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    log.error("线程池线程异常，ThreadName={}", t.getName() , e);
  }

}
