package com.arraypay.market.service;

import com.arraypay.market.constant.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class BaseService {

    @Autowired
    private SysProperties properties;

    protected Pageable buildPageRequest() {
        return buildPageRequest(1);
    }

    protected Pageable buildPageRequest(Integer pageNumber) {
        return buildPageRequest(pageNumber, properties.getPageSize());
    }

    protected Pageable buildPageRequest(Integer pageNumber, Integer pageSize) {
        return buildPageRequest(pageNumber, pageSize, "id");
    }

    protected Pageable buildPageRequest(Integer pageNumber, Integer pageSize, String sortColumn) {
        return buildPageRequest(pageNumber, pageSize, sortColumn, Sort.Direction.DESC);
    }

    protected Pageable buildPageRequest(Integer pageNumber, Integer pageSize, String sortColumn, Sort.Direction sortType) {
        pageNumber = pageNumber == null? 1:pageNumber;
        pageSize = pageSize == null? properties.getPageSize():pageSize;
        sortColumn = sortColumn == null? "id":sortColumn;
        sortType = sortType == null? Sort.Direction.DESC:sortType;
        Sort sort = new Sort(sortType, sortColumn);
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

}
