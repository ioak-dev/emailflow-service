package io.ioak.emailflow.space.aspect;

import io.ioak.emailflow.space.SpaceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UseAdminDbAspect {

    @Autowired
    private SpaceHolder spaceHolder;

    @Around("@annotation(io.ioak.emailflow.space.aspect.UseAdminDb)")
    public Object useAdminDb(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean currentValue = spaceHolder.isUseAdminDb();
        spaceHolder.setUseAdminDb(true);
        Object result = joinPoint.proceed();
        spaceHolder.setUseAdminDb(currentValue);
        return result;
    }
}
