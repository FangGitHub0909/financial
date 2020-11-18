package com.fang.entity.enums;

/**
 * @author fang
 * @name 订单状态
 */

public enum OrderStauts {
    /**111*/
    INIT("初始化"),
    PROCESS("处理中"),
    SUCCESS("成功"),
    FAIL("失败");
    private String desc;
    OrderStauts(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
