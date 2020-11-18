package com.fang.manager.error;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 自定义错误处理
 * @author admin
 */

public class MyErrorController extends BasicErrorController {


    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    /**
     * 获取到错误的返回信息，并返回
     * @param request
     * @param includeStackTrace
     * @return
     */
    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> attrs = super.getErrorAttributes(request, includeStackTrace);
        attrs.remove("error");
        attrs.remove("timestamp");
        attrs.remove("status");
        attrs.remove("exception");
        attrs.remove("path");
        //把整合的错误信息放入返回数据内
        String message = (String) attrs.get("message");
        ErrorEnum code = ErrorEnum.getByCode(message);
        attrs.put("message",code.getMessage());
        attrs.put("code",code.getCode());
        attrs.put("canRetry",code.isCanRetry());
        return attrs;

    }
}
