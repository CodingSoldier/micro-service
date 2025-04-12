package com.github.codingsoldier.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringUtilTest {

  @Test
  void isEndWith() {
    boolean b = StringUtil.isEndWith("adfa。", StringUtil.END_CHAR);
    assertEquals(true, b);
  }
}