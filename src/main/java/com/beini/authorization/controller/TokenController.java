package com.beini.authorization.controller;

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
import com.beini.authorization.entity.UserInfo;
import com.beini.authorization.manager.TokenManager;
import com.beini.authorization.model.TokenModel;
import com.beini.authorization.service.UserService;
import com.beini.core.enums.ResultEnum;
import com.beini.core.utils.ResultVOUtil;
import com.beini.core.vo.ResultVO;

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
@SuppressWarnings("rawtypes")
public class TokenController {
	@Autowired
	private UserService userService;

	@Autowired
	private TokenManager tokenManager;

	@ApiOperation(value = "登录")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResultVO> login(@RequestParam String username, @RequestParam String password) {
		Assert.notNull(username, "username can not be empty");
		Assert.notNull(password, "password can not be empty");

		UserInfo user = userService.findByUsername(username);
		if (/* 未注册 */
			user == null  || 
			/* 密码错误 */
			!user.getUserPassword().equals(password)) {
			/* 提示用户名或密码错误 */
			return new ResponseEntity<>(ResultVOUtil.error(ResultEnum.USERNAME_OR_PASSWORD_ERROR),
					HttpStatus.NOT_FOUND);
		}
		// 生成一个token，保存用户登录状态
		TokenModel model = tokenManager.createToken("" + user.getId());
		return new ResponseEntity<ResultVO>(ResultVOUtil.success(model), HttpStatus.OK);
	}
	
	@ApiOperation(value = "验证token")
	@GetMapping("checkToken")
	public ResponseEntity<ResultVO> checkToken(String authentication) {
		TokenModel tokenModel = tokenManager.getToken(authentication);
		if (tokenModel == null) {
			/* 无token，提示未授权 */
			return new ResponseEntity<>(ResultVOUtil.error(ResultEnum.TOKEN_FAILED), HttpStatus.UNAUTHORIZED);
		}
		boolean checkResult = tokenManager.checkToken(tokenModel);
		if (checkResult) {
			/* token验证通过 */
			return new ResponseEntity<>(ResultVOUtil.success(), HttpStatus.OK);
		} else {
			/* token验证失败，提示未授权 */
			return new ResponseEntity<>(ResultVOUtil.error(ResultEnum.TOKEN_FAILED), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Authorization
	@ApiOperation(value = "退出登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header") })
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ResultVO> logout(String userId) {
		tokenManager.deleteToken("" + userId);
		return new ResponseEntity<>(ResultVOUtil.success(), HttpStatus.OK);
	}
}
