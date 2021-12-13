package com.demo.springmybatis.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class NetUtils {

  private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");

    logger.info(">>>> X-FORWARDED-FOR : " + ip);

    if (ip == null) {
      ip = request.getHeader("Proxy-Client-IP");
      logger.info(">>>> Proxy-Client-IP : " + ip);
    }
    if (ip == null) {
      ip = request.getHeader("WL-Proxy-Client-IP");
      logger.info(">>>> WL-Proxy-Client-IP : " + ip);
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_CLIENT_IP");
      logger.info(">>>> HTTP_CLIENT_IP : " + ip);
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      logger.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
    }
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    logger.info(">>>> Result : IP Address : " + ip);

    return ip;
  }
}
