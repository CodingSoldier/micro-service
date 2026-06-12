package com.github.codingsoldier.common.util;

import static com.github.codingsoldier.common.constant.TraceConstant.X_REQ_TRACE_ID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 链路追踪工具类。
 *
 * @author chenpq05
 * @since 2026-06-11
 */
public class TraceUtil {

  private TraceUtil() {
    // sonar检测
    throw new IllegalStateException("不允许实例化");
  }

  /**
   * 获取已有链路追踪ID，若为空则生成新的链路追踪ID。
   *
   * @param traceId 链路追踪ID
   * @return 非空链路追踪ID
   */
  public static String getOrCreateTraceId(String traceId) {
    if (StringUtils.isNotBlank(traceId)) {
      return traceId;
    }
    return CommonUtil.uuid32();
  }

  /**
   * 从MDC获取链路追踪ID。
   *
   * @return 链路追踪ID，未设置时返回空字符串
   */
  public static String getMdcTraceId() {
    return StringUtils.defaultString(MDC.get(X_REQ_TRACE_ID));
  }

  /**
   * 将链路追踪ID写入MDC，若入参为空则生成新的链路追踪ID。
   *
   * @param traceId 链路追踪ID
   * @return 写入MDC的链路追踪ID
   */
  public static String putMdcTraceId(String traceId) {
    String actualTraceId = getOrCreateTraceId(traceId);
    MDC.put(X_REQ_TRACE_ID, actualTraceId);
    return actualTraceId;
  }

}
