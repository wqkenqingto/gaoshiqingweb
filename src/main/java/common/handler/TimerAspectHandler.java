package common.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspectHandler {

    @Around("execution(* com.mocentre.*.*.service.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed(pjp.getArgs());
        String className = pjp.getTarget().getClass().getName();
        String mothodName = pjp.getSignature().getName();
        time = System.currentTimeMillis() - time;
        System.out.println("--------------- class:" + className + "." + mothodName + " process time: " + time + " ms");
        return retVal;
    }
}
