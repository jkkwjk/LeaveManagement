package com.jkk.leave.entity.POJO;

import lombok.Data;

@Data
public class User {
    /**
     * 用户唯一ID(学号)
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户的类型
     */
    private Integer type;
}