package com.beini.authorization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beini.authorization.entity.UserInfo;
import com.beini.authorization.repository.UserRepository;
import com.beini.authorization.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserInfo findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public Page<UserInfo> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public UserInfo save(UserInfo user) {
		return userRepository.save(user);
	}

	@Override
	public UserInfo findOne(String id) {
		return userRepository.findOne(id);
	}

}
