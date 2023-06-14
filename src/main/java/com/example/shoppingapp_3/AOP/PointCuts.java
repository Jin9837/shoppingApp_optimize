package com.example.shoppingapp_3.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("execution(* com.example.shoppingapp_3.controller.OrderController.*ByAdmin(..))||" +
            "execution(* com.example.shoppingapp_3.controller.OrderController.purchase(..))")
    public void orderControllerLayer(){}

    @Pointcut("execution(* com.example.shoppingapp_3.dao.ProductDao.findAllProductsByBuyer(..))")
    public void cacheLogging(){}

    @Pointcut("execution(* com.example.shoppingapp_3.controller.OrderController.UserOrderSync(..))||" +
            "execution(* com.example.shoppingapp_3.controller.OrderController.UserOrderAsync(..))")
    public void userOrderLayer(){}
}
