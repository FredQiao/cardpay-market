package com.arraypay.market.dao;

import com.arraypay.market.dto.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Page<User> findAll(Pageable pageable);

    User getUserByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

}
