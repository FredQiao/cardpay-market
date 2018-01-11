package com.arraypay.market.service;

import com.arraypay.market.dao.UserRepository;
import com.arraypay.market.dto.entity.User;
import com.arraypay.market.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class UserService extends BaseService{

    @Value("${spring.access_token.expire-time}")
    private Integer atExpireTime;

    @Value("${spring.refresh_token.expire-time}")
    private Integer rtExpireTime;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisService redisService;

    public Page<User> listUsers(Integer pageNumber){
        Pageable request = buildPageRequest(pageNumber);
        return userRepository.findAll(request);
    }

    public User getUserByUsernameAndPwd(String username, String password){
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Cacheable(value = "users")
    public User getUserById(String id){
        return userRepository.getOne(id);
    }

    @Transactional
    public User genToken(User user){
        String accessToken = UUID.randomUUID().toString().replaceAll("-","");
        Date atExpiredTime = DateUtils.getNewDateByAddSecond(atExpireTime);
        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        Date rtExpiredTime = DateUtils.getNewDateByAddSecond(rtExpireTime);

        /**
         * Token存在数据库中
         */
        user.setAccessToken(accessToken);
        user.setAtExpiredTime(atExpiredTime);
        user.setRefreshToken(refreshToken);
        user.setRtExpiredTime(rtExpiredTime);
        user = userRepository.save(user);

        /**
         * Token存在Redis中
         */
        redisService.set("access_token_id", accessToken, atExpiredTime.getTime());
        redisService.set("refresh_token_id", refreshToken, rtExpiredTime.getTime());
        return user;
    }
}
