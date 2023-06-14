package com.example.shoppingapp_3.handler;

import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.exception.CustomizedException;
import com.example.shoppingapp_3.exception.CustomizedExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiRestResponse> handleException(Exception e){
        System.out.println(e.getMessage());
        return new ResponseEntity(ApiRestResponse.error(CustomizedExceptionEnum.SYSTEM_ERROR), HttpStatus.OK);
    }

    @ExceptionHandler(value = {CustomizedException.class})
    public ResponseEntity<ApiRestResponse> handleCustomizedException(CustomizedException e) {
        return new ResponseEntity(ApiRestResponse.error(e.getCode(), e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ApiRestResponse> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity(ApiRestResponse.error(CustomizedExceptionEnum.ACCESS_IS_DENIED), HttpStatus.OK);
    }
}
