package com.fang.entity.enums;

/**
 * @author fang
 * @name 订单类型
 */

public enum OrderType {
    /**111*/
    AUDITING("审核中"),
    IN_SELL("销售中"),
    LOCKED("暂停销售"),
    FINISHED("已结束");
    private String desc;
    OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
