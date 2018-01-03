package com.arraypay.market.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @NonNull
    private Integer id;

    /**
     * 用户名
     */
    @Column
    @NonNull
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @Column
    @NonNull
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
