package com.example.shoppingapp_3.handler;

import com.alibaba.fastjson.JSON;
import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.exception.CustomizedExceptionEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(JSON.toJSONString(ApiRestResponse.error(CustomizedExceptionEnum.ACCESS_DENIED)));
    }
}
