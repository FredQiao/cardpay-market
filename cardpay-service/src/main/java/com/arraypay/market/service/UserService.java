package com.arraypay.market.service;

import com.arraypay.market.dao.UserRepository;
import com.arraypay.market.dto.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class UserService extends BaseService{

    @Autowired
    private UserRepository userRepository;

    public Page<User> listUsers(int pageNumber){
        Pageable request = buildPageRequest(pageNumber);
        return userRepository.findAll(request);
    }
}
