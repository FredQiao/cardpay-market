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
@Table(name = "country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country implements Serializable{

    @Id
    @NonNull
    private String id;

    /**
     * 国家或地区英文名
     */
    @Column
    @NonNull
    private String ename;

    /**
     * 国家或地区中文名
     */
    @Column
    @NonNull
    private String name;


    /**
     * 国际域名缩写
     */
    @Column
    private String domainCode;

    /**
     * 电话代码
     */
    @Column
    private String telCode;

    public Country() {
        this.id = UUID.randomUUID().toString().replaceAll("-","");
    }

    public Country(String id, String ename, String name, String domainCode, String telCode) {
        this.id = id;
        this.ename = ename;
        this.name = name;
        this.domainCode = domainCode;
        this.telCode = telCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getTelCode() {
        return telCode;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }
}
