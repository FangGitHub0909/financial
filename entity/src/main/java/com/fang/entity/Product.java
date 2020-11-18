package com.fang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品
 *
 * @author fang
 */
@Data
@Entity(name = "product")
public class Product {
    @Id
    private String id;
    private String name;
    /**
     * @See package com.fang.entity.enums;
     * */
    private String status;
    private String createUser;
    private String updateUser;
    /**
     * 起投资金
     */
    private BigDecimal thresholdAmount;
    /**
     * 起投步长
     */
    private BigDecimal stepAmount;
    /**
     * 收益率
     */
    private BigDecimal rewardRate;
    /**
     * 锁定期
     */
    private Integer lockTerm;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    public Product(String id, String name, String status, BigDecimal thresholdAmount, BigDecimal stepAmount, BigDecimal rewardRate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.thresholdAmount = thresholdAmount;
        this.stepAmount = stepAmount;
        this.rewardRate = rewardRate;
    }

    public Product() {
    }

    public Product(String id, String name, String status, String createUser, String updateUser, BigDecimal thresholdAmount, BigDecimal stepAmount, BigDecimal rewardRate, Integer lockTerm, Date createAt, Date updateAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.thresholdAmount = thresholdAmount;
        this.stepAmount = stepAmount;
        this.rewardRate = rewardRate;
        this.lockTerm = lockTerm;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
