package com.beini.authorization.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.beini.authorization.annotation.Authorization;
import com.beini.authorization.config.Constants;
import com.beini.authorization.manager.TokenManager;
import com.beini.authorization.model.TokenModel;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * 
 * @see com.beini.authorization.annotation.Authorization
 * @author lb_chen
 * @date 2018-04-18 14:00
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenManager manager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/* 如果不是映射到方法直接通过 */
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		/* 从header中得到token */
		String authorization = request.getHeader(Constants.AUTHORIZATION);
		/* 从parameter中获得token */
		// String authorization = request.getParameter(Constants.AUTHORIZATION);
		TokenModel model = manager.getToken(authorization);
		/* 验证token */
		if (manager.checkToken(model)) {
			/* 如果token验证成功，将token对应的用户id存在request中，便于之后注入 */
			request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
			return true;
		}
		/* 如果验证token失败，并且方法注明了Authorization，返回401错误 */
		if (method.getAnnotation(Authorization.class) != null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		return true;
	}
}
