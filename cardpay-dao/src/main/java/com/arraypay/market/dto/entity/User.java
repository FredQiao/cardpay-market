package com.arraypay.market.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{

	private static final long serialVersionUID = 2547875549887343151L;

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

}
