package com.lishuai.highconcurrentsecondkill.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.websocket.ClientEndpoint;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lishuai
 * @date 2022/8/28
 */

@Slf4j
@Component
@Scope
@Aspect
@Order(1) //order越小越是最先执行，但更重要是最先执行的最后结果
public class LockAspect {


    private static Lock lock = new ReentrantLock(true);

    //Service层切点 用于记录错误日志
    @Pointcut("@annotation(com.lishuai.highconcurrentsecondkill.aspect.ServerLock)")
    public void lockAspect(){

    }

    public Object around(ProceedingJoinPoint joinPoint){
        lock.lock();
        Object obj = null;

        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException();
        }finally {
            lock.unlock();
        }
        return obj;
    }
}

