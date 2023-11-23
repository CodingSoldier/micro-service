package com.github.codingsoldier.starter.nacos.graceful.constant;

/**
 * 常量
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class Constant {

   private Constant() {
      // sonar
   }

   /**
    * nacos客户端下线 URI
    */
   public static final String URI_DEREGISTER_INSTANCE = "/nacos/graceful/deregister/instance";

}
