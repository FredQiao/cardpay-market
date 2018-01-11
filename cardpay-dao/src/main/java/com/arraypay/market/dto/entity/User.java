package com.arraypay.market.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{

    @Id
    @NonNull
    private String id;

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

    /**
     * access_token
     */
    @Column
    private String accessToken;

    /**
     * access_token过期时间
     */
    @Column
    private Date atExpiredTime;

    /**
     * refresh_token
     */
    @Column
    private String refreshToken;

    /**
     * refresh_token过期时间
     */
    @Column
    private Date rtExpiredTime;


    public User(){
        this.id = UUID.randomUUID().toString().replaceAll("-","");
    }

    public User(String id, String username, String password, String accessToken, Date atExpiredTime, String refreshToken, Date rtExpiredTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
        this.atExpiredTime = atExpiredTime;
        this.refreshToken = refreshToken;
        this.rtExpiredTime = rtExpiredTime;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAtExpiredTime() {
        return atExpiredTime;
    }

    public void setAtExpiredTime(Date atExpiredTime) {
        this.atExpiredTime = atExpiredTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRtExpiredTime() {
        return rtExpiredTime;
    }

    public void setRtExpiredTime(Date rtExpiredTime) {
        this.rtExpiredTime = rtExpiredTime;
    }
}
