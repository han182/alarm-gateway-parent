package com.pepris.system.exception;

import com.pepris.common.result.Result;
import com.pepris.common.result.ResultCodeEnum;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //异常处理器，加上才能执行方法
    @ResponseBody
    // 全局异常处理
    public Result error(Exception e) {
        e.printStackTrace();// 打印异常信息
        return Result.fail().message("执行了全局异常处理");

    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    @ExceptionHandler(HansException.class)
    @ResponseBody
    public Result error(HansException e){
        e.printStackTrace();
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前功能的操作权限");
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.build(null, ResultCodeEnum.PERMISSION);
    }





}