package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author chenpq05
 * @since 2022/2/23 14:14
 */
@Slf4j
public class MD5Util {

  private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

  /**
   * 将1个字节（1 byte = 8 bit）转为 2个十六进制位 1个16进制位 = 4个二进制位 （即4 bit） 转换思路：最简单的办法就是先将byte转为10进制的int类型，然后将十进制数转十六进制
   */
  private static String byteToHexString(byte b) {
    // byte类型赋值给int变量时，java会自动将byte类型转int类型，从低位类型到高位类型自动转换
    int n = b;

    // 将十进制数转十六进制
    if (n < 0) {
      n += 256;
    }
    int d1 = n / 16;
    int d2 = n % 16;

    // d1和d2通过访问数组变量的方式转成16进制字符串；比如 d1 为12 ，那么就转为"c"; 因为int类型不会有a,b,c,d,e,f等表示16进制的字符
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * 将字节数组里每个字节转成2个16进制位的字符串后拼接起来
   */
  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  /**
   * MD5算法，统一返回大写形式的摘要结果，默认固定长度是 128bit 即 32个16进制位 String origin ：需要进行MD5计算的字符串 String charsetname
   * ：MD5算法的编码
   */
  public static String md5_32(String origin, String charsetname) {
    String resultString = null;
    try {
      // 1，创建MessageDigest对象
      MessageDigest md = MessageDigest.getInstance("MD5");
      // 2，向MessageDigest传送信息;传入的信息需要转化为指定编码的字节数组
      md.update(origin.getBytes(charsetname));
      // 3，计算摘要
      byte[] bytesResult = md.digest();

      // 第2步和第3步可以合并成下面一步
      // byte[] bytesResult = md.digest(origin.getBytes(charsetname));

      // 4,将字节数组转换为16进制位
      resultString = byteArrayToHexString(bytesResult);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return resultString;
  }

  public static String md5_32(byte[] input) {
    String resultString = null;
    try {
      // 1，创建MessageDigest对象
      MessageDigest md = MessageDigest.getInstance("MD5");
      // 2，向MessageDigest传送信息;传入的信息需要转化为指定编码的字节数组
      md.update(input);
      // 3，计算摘要
      byte[] bytesResult = md.digest();

      // 第2步和第3步可以合并成下面一步
      // byte[] bytesResult = md.digest(origin.getBytes(charsetname));

      // 4,将字节数组转换为16进制位
      resultString = byteArrayToHexString(bytesResult);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return resultString;
  }

  /**
   * 加密
   * @param content content
   * @return
   */
  public static String md5_32(String content) {
    return md5_32(content, "utf-8");
  }


  public static void main(String[] args) {
    String origin = "1234567890";

    // 默认MD5计算得到128 bit的摘要，即32个 16进制位
    String result_32 = md5_32(origin);
    System.out.println(result_32);  // E807F1FCF82D132F9BB018CA6738A19F

  }

  //public static String getMD5(String content) {
  //  try {
  //    MessageDigest digest = MessageDigest.getInstance("MD5");
  //    digest.update(content.getBytes());
  //    return getHashString(digest);
  //  } catch (NoSuchAlgorithmException e) {
  //    e.printStackTrace();
  //  }
  //  return null;
  //}

  //private static String getHashString(MessageDigest digest) {
  //  StringBuilder builder = new StringBuilder();
  //  for (byte b : digest.digest()) {
  //    builder.append(Integer.toHexString((b >> 4) & 0xf));
  //    builder.append(Integer.toHexString(b & 0xf));
  //  }
  //  return builder.toString();
  //}

}
