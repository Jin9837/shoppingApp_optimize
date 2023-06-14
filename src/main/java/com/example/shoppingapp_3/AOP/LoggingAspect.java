package com.example.shoppingapp_3.AOP;

import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.domain.dto.ProductDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("com.example.shoppingapp_3.AOP.PointCuts.orderControllerLayer()")
    public ApiRestResponse logStartAndEndTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        long start = System.currentTimeMillis();
        Date startDate = new Date();
        logger.info("From LoggingAspect.logStartAndEndTime: " + proceedingJoinPoint.getSignature());
        logger.info("Start Time: " + startDate);

        // Invoke the actual object
        ApiRestResponse result = (ApiRestResponse) proceedingJoinPoint.proceed();

        // after
        long end = System.currentTimeMillis();
        Date endDate = new Date(end);
        logger.info("End time: " + endDate);
        return result;
    }


    @Around("com.example.shoppingapp_3.AOP.PointCuts.cacheLogging()")
    public List<ProductDto> logCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        long start = System.currentTimeMillis();
        Date startDate = new Date(start);
        logger.info("From LoggingAspect.logCache: " + proceedingJoinPoint.getSignature());
        logger.info("Start time: " + startDate);

        //Invoke the actual object
        List<ProductDto> result = (List<ProductDto>) proceedingJoinPoint.proceed();

        // after
        long end = System.currentTimeMillis();
        Date endDate = new Date(end);
        logger.info("End time: " + endDate);

        // calculate the time
        long time = end - start;
        logger.info("Time: " + time);

        return result;
    }


    @Around("com.example.shoppingapp_3.AOP.PointCuts.userOrderLayer()")
    public ApiRestResponse asyncLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // before
        long start = System.currentTimeMillis();
        Date startDate = new Date(start);
        logger.info("From LoggingAspect.logCache: " + proceedingJoinPoint.getSignature());
        logger.info("Start time: " + startDate);

        //Invoke the actual object
        ApiRestResponse result = (ApiRestResponse) proceedingJoinPoint.proceed();

        // after
        long end = System.currentTimeMillis();
        Date endDate = new Date(end);
        logger.info("End time: " + endDate);

        // calculate the time
        long time = end - start;
        logger.info("Time: " + time);

        return result;
    }
}
