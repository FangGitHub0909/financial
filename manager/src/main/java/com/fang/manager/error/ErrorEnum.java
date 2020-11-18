package com.fang.manager.error;

import org.omg.CORBA.UNKNOWN;

/**
 * 错误种类
 */
public enum ErrorEnum {
    /**
     * 编号异常
     */
    ID_NOT_NULL("F001","编号不可为空",false),
    //省略一些异常
    /**
     * 未知异常
     */
    UNKNOWN("999","未知异常",false);
    private String code;
    private String message;
    private boolean canRetry;

     ErrorEnum(String code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }
    public static ErrorEnum  getByCode(String code){
         for (ErrorEnum e : ErrorEnum.values()){
             if (e.code.equals(code)){
                 return e;
             }
         }
         return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCanRetry() {
        return canRetry;
    }
}
