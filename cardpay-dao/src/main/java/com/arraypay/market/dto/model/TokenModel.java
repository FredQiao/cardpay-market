package com.arraypay.market.dto.model;

import java.util.Date;

public class TokenModel {

    private String userId;
    private String token;
    private Date expiredTime;

    public TokenModel(String userId, String token, Date expiredTime) {
        this.userId = userId;
        this.token = token;
        this.expiredTime = expiredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}
