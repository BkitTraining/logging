package com.example.demo.logging.aspect;

import com.example.demo.logging.annotation.LogMethod;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

  @Around("@within(logMethod) || @annotation(logMethod)")
  public Object logMethodExecution(ProceedingJoinPoint pjp, LogMethod logMethod) throws Throwable {
    final MethodSignature signature = (MethodSignature) pjp.getSignature();
    final Method method = signature.getMethod();
    final StopWatch stopWatch = new StopWatch();
    try {
      final String arguments = IntStream.iterate(0, i -> i + 1)
          .limit(Math.min(signature.getParameterNames().length, pjp.getArgs().length))
          .mapToObj(i -> signature.getParameterNames()[i] + "=" + pjp.getArgs()[i])
          .collect(Collectors.joining(","));
      log.info("Start execution of {} with arguments: {}", method, arguments);
      stopWatch.start();
      final Object result = pjp.proceed();
      stopWatch.stop();
      log.info("Finish execution of {} (running {} ms)", method, stopWatch.getTime());
      return result;
    } catch (Exception ex) {
      stopWatch.stop();
      log.error("Fail execution of {} (running {} ms)", method, stopWatch.getTime(), ex);
      throw ex;
    }
  }

  @AfterThrowing(value = "@within(logMethod) || @annotation(logMethod)", throwing = "error")
  public void logAfterThrowing(JoinPoint pjp, LogMethod logMethod, Throwable error) throws InterruptedException {
    final MethodSignature signature = (MethodSignature) pjp.getSignature();
    final Method method = signature.getMethod();
    final String arguments = IntStream.iterate(0, i -> i + 1)
        .limit(Math.min(signature.getParameterNames().length, pjp.getArgs().length))
        .mapToObj(i -> signature.getParameterNames()[i] + "=" + pjp.getArgs()[i])
        .collect(Collectors.joining(","));
    log.error("handle exception in {} with cause = {}, arguments = {}",
        method,
        error.getCause() != null ? error.getCause() : "NULL",
        arguments);
    Thread.sleep(2000);
  }

}
