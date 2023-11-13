package com.github.codingsoldier.starter.nacos.graceful.constant;

public class Constant {

   private Constant() {
      // sonar
   }

   /**
    * nacos客户端下线 URI
    */
   public static final String URI_DEREGISTER_INSTANCE = "/nacos/graceful/deregister/instance";

}
