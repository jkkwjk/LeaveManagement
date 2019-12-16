package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User {
    @JsonIgnore
    private Integer id;

    private String name;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Integer type;

    private String avatar;

    private String eMail;
}