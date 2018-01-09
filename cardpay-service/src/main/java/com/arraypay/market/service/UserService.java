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

    public Page<User> listUsers(Integer pageNumber){
        Pageable request = buildPageRequest(pageNumber);
        return userRepository.findAll(request);
    }

    public User getUserByUsernameAndPwd(String username, String password){
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(String id){
        return userRepository.getOne(id);
    }

    public User getUserByRefreshToken(String refreshToken){
        return userRepository.getUserByRefreshToken(refreshToken);
    }
}
