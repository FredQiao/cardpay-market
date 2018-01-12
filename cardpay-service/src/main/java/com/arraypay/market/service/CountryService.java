package com.arraypay.market.service;

import com.alibaba.fastjson.JSON;
import com.arraypay.market.dao.CountryRepository;
import com.arraypay.market.dto.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class CountryService extends BaseService{

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RedisService redisService;

    public List<Country> listCountries(){
        List<Country> countries = redisService.getList("countries", Country.class);
        if(countries != null){
            return countries;
        }

        countries = countryRepository.findAll();
        redisService.set("countries", JSON.toJSONString(countries), 100, TimeUnit.DAYS);
        return countries;
    }
}
