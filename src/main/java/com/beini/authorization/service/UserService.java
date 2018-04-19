package com.beini.authorization.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beini.authorization.entity.UserInfo;

public interface UserService {
	UserInfo findByUsername(String name);
	Page<UserInfo> findAll(Pageable pageable);
	UserInfo save(UserInfo user);
	UserInfo findOne(String id);
}
