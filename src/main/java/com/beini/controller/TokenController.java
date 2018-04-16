package com.beini.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beini.authorization.annotation.Authorization;
import com.beini.authorization.manager.TokenManager;
import com.beini.authorization.model.TokenModel;
import com.beini.config.ResultStatus;
import com.beini.domain.User;
import com.beini.model.ResultModel;
import com.beini.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * 
 * @author lb_chen
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {
	@SuppressWarnings("unused")
	private Logger LOG = LoggerFactory.getLogger(TokenController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private TokenManager tokenManager;

	@ApiOperation(value = "登录")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResultModel> login(@RequestParam String username, @RequestParam String password) {
		Assert.notNull(username, "username can not be empty");
		Assert.notNull(password, "password can not be empty");

		User user = userService.findByUsername(username);
		if (user == null /* 未注册 */ || !user.getPassword().equals(password)/* 密码错误 */) {
			/* 提示用户名或密码错误 */
			return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR),
					HttpStatus.NOT_FOUND);
		}
		// 生成一个token，保存用户登录状态
		TokenModel model = tokenManager.createToken("" + user.getId());
		return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
	}

	

	@ApiOperation(value = "验证token")
	@GetMapping("checkToken")
	public ResponseEntity<ResultModel> checkToken(String authentication) {
		TokenModel tokenModel = tokenManager.getToken(authentication);
		if (tokenModel == null) {
			/* 无token，提示未授权 */
			return new ResponseEntity<>(ResultModel.error(ResultStatus.TOKEN_FAILED), HttpStatus.UNAUTHORIZED);
		}
		boolean checkResult = tokenManager.checkToken(tokenModel);
		if (checkResult) {
			/* token验证通过 */
			return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
		} else {
			/* token验证失败，提示未授权 */
			return new ResponseEntity<>(ResultModel.error(ResultStatus.TOKEN_FAILED), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Authorization
	@ApiOperation(value = "退出登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header") })
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ResultModel> logout(String userId) {
		tokenManager.deleteToken("" + userId);
		return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
	}
}
