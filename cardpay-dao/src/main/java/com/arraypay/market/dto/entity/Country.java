package com.arraypay.market.dto.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Entity
@Table(name = "country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country implements Serializable{

	private static final long serialVersionUID = 7171297355973621130L;

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
}
