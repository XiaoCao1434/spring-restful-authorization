package com.beini.authorization.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beini.authorization.model.User;

public interface UserService {
	User findByUsername(String name);
	Page<User> findAll(Pageable pageable);
	User save(User user);
	User findOne(String id);
}
