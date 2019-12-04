package com.jkk.leave.entity.POJO;

import lombok.Data;

import java.io.Serializable;

/**
 * card
 * @author 
 */
@Data
public class Card implements Serializable {
    /**
     * 饭卡绑定的用户ID
     */
    private Integer userId;

    /**
     * 饭卡余额
     */
    private String money;

    private static final long serialVersionUID = 1L;
}