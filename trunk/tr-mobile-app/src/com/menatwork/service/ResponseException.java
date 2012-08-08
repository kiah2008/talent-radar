package com.menatwork.service;

public class ResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResponseException() {
		super();
	}

	public ResponseException(final String detailMessage,
			final Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ResponseException(final String detailMessage) {
		super(detailMessage);
	}

	public ResponseException(final Throwable throwable) {
		super(throwable);
	}

}
