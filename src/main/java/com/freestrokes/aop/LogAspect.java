package com.freestrokes.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // TODO: @Around 어노테이션
    // ProceedingJoinPoint를 파라미터로 받을 수 있는데 이건 해당 어노테이션이 붙은 메서드를 받아오도록 동작.
    // @Around("execution(* com.freestrokes..*(..))") 이와 같이 execution을 사용하면 해당 패키지 하위 모든 클래스에 적용

    @Around("@annotation(com.freestrokes.aop.LogExecutionTime)")
//    @Around("execution(* com.freestrokes..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // LogExcutionTime 어노테이션이 붙은 메서드를 ProceedingJoinPoint로 받아와서 실행
        Object proceed = joinPoint.proceed();

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());

        return proceed;
    }

}
