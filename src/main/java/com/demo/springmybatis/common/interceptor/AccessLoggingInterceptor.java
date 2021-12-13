package com.demo.springmybatis.common.interceptor;

import com.demo.springmybatis.common.domain.AccessLog;
import com.demo.springmybatis.common.service.AccessLogService;
import com.demo.springmybatis.common.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AccessLoggingInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(AccessLoggingInterceptor.class);

  @Autowired
  private AccessLogService service;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    String requestUri = request.getRequestURI();

    String remoteAddr = NetUtils.getIp(request);

    logger.info("requestURL : " + requestUri);
    logger.info("remoteAddr : " + remoteAddr);

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();

      Class clazz = method.getDeclaringClass();

      String className = clazz.getName();
      String classSimpleName = clazz.getSimpleName();
      String methodName = method.getName();

      logger.info("[ACCESS CONTROLLER] " + className + "." + methodName);

      AccessLog accessLog = new AccessLog();

      accessLog.setRequestUri(requestUri);
      accessLog.setRemoteAddr(remoteAddr);
      accessLog.setClassName(className);
      accessLog.setClassSimpleName(classSimpleName);
      accessLog.setMethodName(methodName);

      service.register(accessLog);
    } else {
      logger.info("handler : " + handler);
    }
  }
}
