package com.arraypay.market.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class BaseService {

    @Value("${spring.paging.default-size}")
    int PAGE_SIZE;

    //构建PageRequest
    protected Pageable buildPageRequest(int pageNumber) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return new PageRequest(pageNumber - 1, PAGE_SIZE, sort);
    }

    //构建PageRequest
    protected Pageable buildPageRequest(int pageNumber, String sortColumn) {
        Sort sort = new Sort(Sort.Direction.DESC, sortColumn);
        return new PageRequest(pageNumber - 1, PAGE_SIZE, sort);
    }
}
