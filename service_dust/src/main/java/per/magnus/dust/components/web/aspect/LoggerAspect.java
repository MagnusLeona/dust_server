package per.magnus.dust.components.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Order(1)
@Component
public class LoggerAspect {

    @Pointcut("execution( * per.magnus.dust.business.controller..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object aroundLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取客户端IP
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        String remoteHost = requestAttributes.getRequest().getRemoteHost();
//        MDC.put("ip", remoteHost);
        // 流水号生成器，可以存到ThreadLocal里面去
        MDC.put("serialNo", "123");
        // 打印日志
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = signature.getMethod().getName();
        log.info("[uri: {}] transaction start | ", name);
        Object proceed;
        try {
            proceed = proceedingJoinPoint.proceed();
            log.info("[uri: {}] transaction end on success | ", name);
        } catch (Throwable e) {
            log.info("[uri: {}] transaction end on error | ", name);
            throw e;
        } finally {
            MDC.clear();
        }
        return proceed;
    }
}
