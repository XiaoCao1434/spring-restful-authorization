package com.beini.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefuseConnectionException extends RuntimeException{
	private Logger LOG = LoggerFactory.getLogger(RefuseConnectionException.class);
	private static final long serialVersionUID = 500L;
	
	public RefuseConnectionException(String message){
		LOG.error(message);
	}
	@Override
	public void printStackTrace() {
		System.out.println("redis或者mysql连接异常");
		/*不再打印其他异常*/
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}
	
}
