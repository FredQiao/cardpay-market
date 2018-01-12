package com.arraypay.market.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SysProperties {

    @Value("${spring.access_token.expire-time}")
    private Integer atExpireTime;

    @Value("${spring.refresh_token.expire-time}")
    private Integer rtExpireTime;

    @Value("${spring.paging.default-size}")
    private Integer pageSize;

    @Value("${spring.qcloudsms.appid}")
    private Integer appid;

    @Value("${spring.qcloudsms.appkey}")
    private String appKey;

    @Value("${system.title}")
    private String sysTitle;

    public Integer getAtExpireTime() {
        return atExpireTime;
    }

    public Integer getRtExpireTime() {
        return rtExpireTime;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getAppid() {
        return appid;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSysTitle() {
        return sysTitle;
    }
}
