package com.arraypay.market.dto.model;

import java.util.Date;

public class TokenModel {

    private String userId;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiredTime;

    public TokenModel() {}

    public TokenModel(String userId, String accessToken, String refreshToken, Date accessTokenExpiredTime) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getAccessTokenExpiredTime() {
        return accessTokenExpiredTime;
    }

    public void setAccessTokenExpiredTime(Date accessTokenExpiredTime) {
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }
}
