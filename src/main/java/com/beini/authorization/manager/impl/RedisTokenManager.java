package com.beini.authorization.manager.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.beini.authorization.config.Constants;
import com.beini.authorization.manager.TokenManager;
import com.beini.authorization.model.TokenModel;
import com.beini.core.utils.StringUtil;

/**
 * 通过Redis存储和验证token的实现类
 * 
 * @see com.beini.authorization.manager.TokenManager
 * @author lb_chen
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class RedisTokenManager implements TokenManager {
	@Qualifier
	@Resource(name = "redis")
	private RedisTemplate redis;
	/**
	 * 	创建token
	 */
	@Override
	public TokenModel createToken(String userId) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		TokenModel model = new TokenModel(userId, token);
		// 存储到redis并设置过期时间
		redis.boundValueOps(""+userId).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		return model;
	}
	/**
	 * 获取token
	 */
	@Override
	public TokenModel getToken(String authentication) {
		if (authentication == null ||"".equals(authentication)|| authentication.length() == 0) {
			return null;
		}
		String[] param = authentication.split("_");
		if (param.length != StringUtil.CHAR_INT_TWO) {
			return null;
		}
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		String token = param[1];
		return new TokenModel(param[0], token);
	}
	/**
	 * 检查token
	 */
	@Override
	public boolean checkToken(TokenModel model) {
		if (model == null) {
			return false;
		}
		String token = ""+redis.boundValueOps(model.getUserId()).get();
		if (token == null || !token.equals(model.getToken())) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
		redis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		return true;
	}
	/**
	 * 删除token
	 */
	@Override
	public void deleteToken(String userId) {
		redis.delete(userId);
	}
}
