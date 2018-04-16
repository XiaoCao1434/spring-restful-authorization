package com.beini.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class UserControllerTest {
	@Test
	public void testGetOne() {
		CloseableHttpClient httpCilent2 = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置连接超时时间
				.setConnectionRequestTimeout(5000) // 设置请求超时时间
				.setSocketTimeout(5000).setRedirectsEnabled(true)// 默认允许自动重定向
				.build();
		// HttpGet httpGet2 = new HttpGet("http://localhost:8080/user/getOne?id=1");
		HttpGet httpGet2 = new HttpGet(
				"http://localhost:8080/user/getOne?id=1&authorization=1_1c0b966c95f943afa3db3656ce503fb4");
		httpGet2.setConfig(requestConfig);
		// httpGet2.setHeader("authorization", "1_1c0b966c95f943afa3db3656ce503fb4");
		String srtResult = "";
		try {
			HttpResponse httpResponse = httpCilent2.execute(httpGet2);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
				System.err.println(srtResult);
			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
				System.out.println("请求异常");
			} else if (httpResponse.getStatusLine().getStatusCode() == 401) {
				System.out.println("未授权");
			} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
				System.out.println("程序错误");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpCilent2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
