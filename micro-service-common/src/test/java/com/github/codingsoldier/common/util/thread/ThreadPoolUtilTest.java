//package com.github.codingsoldier.common.util.thread;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.github.codingsoldier.common.exception.HttpStatus5xxException;
//import java.util.concurrent.Future;
//import org.junit.jupiter.api.Test;
//
//class ThreadPoolUtilTest {
//
//  @Test
//  void execute() {
//    ThreadPoolUtil.execute(() -> assertTrue(true));
//  }
//
//  @Test
//  void getExecutor() {
//    assertNotNull(ThreadPoolUtil.getExecutor());
//  }
//
//  @Test
//  void executeException() {
//    ThreadPoolUtil.execute(() -> {
//      throw new HttpStatus5xxException("线程池中抛出异常");
//    });
//  }
//
//  @Test
//  void submit() throws Exception {
//    Future<String> resp = ThreadPoolUtil.submit(() -> "返回结果");
//    assertEquals("返回结果", resp.get());
//  }
//
//}