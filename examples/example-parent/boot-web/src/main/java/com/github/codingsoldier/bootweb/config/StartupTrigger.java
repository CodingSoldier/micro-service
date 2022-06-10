// package com.github.codingsoldier.bootweb.config;
//
// import ch.qos.logback.classic.spi.ILoggingEvent;
// import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
// import ch.qos.logback.core.rolling.TriggeringPolicyBase;
//
// import java.io.File;
//
// public class StartupTrigger extends TriggeringPolicyBase<ILoggingEvent> {
//
//     private boolean triggerRollover = true;
//
//     @Override
//     public boolean isTriggeringEvent(File activeFile, ILoggingEvent event) {
//         if (super.isStarted() && triggerRollover && event.getLoggerName().equals("com.github.codingsoldier.bootweb.BootWebApplication") && event.getMessage().equals("清空日志")) {
//         // if (super.isStarted() && triggerRollover && event.getLoggerName().equals("org.springframework.boot.web.embedded.tomcat.TomcatWebServer")) {
//             triggerRollover = false;
//             TimeBasedRollingPolicy<Object> objectTimeBasedRollingPolicy = new TimeBasedRollingPolicy<>();
//             objectTimeBasedRollingPolicy.isTriggeringEvent(activeFile, event);
//             return true;
//         }
//         // super.isTriggeringEvent(activeFile, event);
//         return false;
//     }
//
// }
