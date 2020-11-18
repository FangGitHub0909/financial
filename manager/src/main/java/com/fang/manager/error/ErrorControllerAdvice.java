package com.fang.manager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一错误处理
 */
//@ControllerAdvice
@RequestMapping("com.fang.manager.controller")
public class ErrorControllerAdvice {

    /**
     * 需要处理返回的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e ){
        Map<String, Object> attrs = new HashMap<>();
        String errorCode = e.getMessage();
        //把整合的错误信息放入返回数据内
        attrs.put("message",ErrorEnum.getByCode((String) attrs.get("message")).getMessage());
        attrs.put("code",ErrorEnum.getByCode((String) attrs.get("message")).getCode());
        attrs.put("canRetry",ErrorEnum.getByCode((String) attrs.get("message")).isCanRetry());
        //测试区别
        attrs.put("type","advice");
        return new ResponseEntity(attrs, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
