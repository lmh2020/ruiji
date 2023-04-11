package com.lmh.ruiji.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理Aop
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobeExceptionHandler {
    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptioHandle(SQLIntegrityConstraintViolationException ex) {
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split=ex.getMessage().split("");
            String msg=split[2]+"已存在";
            return R.error(msg);
        }
        return R.error("失败");
    }

    /**
     * 自定义业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> CustomException(CustomException ex) {
        String message = ex.getMessage();
        return R.error(message);
    }
}
