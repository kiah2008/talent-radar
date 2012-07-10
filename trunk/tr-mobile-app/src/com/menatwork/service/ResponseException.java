package com.menatwork.service;

public class ResponseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public ResponseException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public ResponseException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}
