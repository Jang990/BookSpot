package com.bookspot.global.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/** @see BasicLog */
@Slf4j
@Aspect
@Component
public class BasicLogAspect {
    private static final double NANOSECONDS_TO_MILLISECONDS = 1_000_000.0;
    private static final int LOG_ID_LENGTH = 8;

    @Around("@within(com.bookspot.global.log.BasicLog) || @annotation(com.bookspot.global.log.BasicLog)")
    public Object checkTime(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        long start, end;

        start = System.nanoTime();
        Object[] args = pjp.getArgs();
        String classDotMethod = getClassDotMethod(pjp);
        String logId = createLogId();

        try {
            result = pjp.proceed(); // 핵심 기능 실행
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            end = System.nanoTime();
            long runningTimeNano = end - start;
            double runningTimeMillis = runningTimeNano / NANOSECONDS_TO_MILLISECONDS;
            log.info("[{}] {} - {} => Time: {} ms | Params : {}", logId, classDotMethod, args, runningTimeMillis, Arrays.toString(args));
        }
    }

    /**
     * 로그 판단을 위해 로그 ID 생성
     * ex) 880aea9c
     * @return
     */
    private String createLogId() {
        return UUID.randomUUID().toString().substring(0, LOG_ID_LENGTH);
    }

    /**
     * {클래스명}.{메소드명}으로 반환
     */
    private static String getClassDotMethod(ProceedingJoinPoint pjp) {
        String targetClassPath = pjp.getTarget().getClass().toString();
        int classNameIdx = targetClassPath.lastIndexOf(".") + 1;
        Signature signature = pjp.getSignature();
        return targetClassPath.substring(classNameIdx) + "." + signature.getName();
    }
}
