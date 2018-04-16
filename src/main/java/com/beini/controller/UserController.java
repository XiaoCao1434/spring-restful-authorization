package com.beini.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beini.authorization.annotation.Authorization;
import com.beini.config.Constants;
import com.beini.domain.User;
import com.beini.service.UserService;
import com.google.gson.Gson;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Authorization
	@ApiOperation(value = "获取全部用户")
	@GetMapping("getPage")
	public Page<User> getPage(@RequestHeader(name = Constants.AUTHORIZATION, required = true, defaultValue = "") String authorization) {
		Pageable pageable = new PageRequest(0, 5);
		return userService.findAll(pageable);
	}

	@ApiOperation(value = "获取具体用户")
	@GetMapping("getOne")
	public String getOne(
			//@RequestHeader(name = Constants.AUTHORIZATION, required = true, defaultValue = "") String authorization,
			String id) {
		LOG.error("user Id :" + id);
		User user = userService.findOne(id);
		return new Gson().toJson(user);
	}
}
