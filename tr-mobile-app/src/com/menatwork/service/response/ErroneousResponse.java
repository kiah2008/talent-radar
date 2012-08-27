package com.menatwork.service.response;

public enum ErroneousResponse implements Response {

	INSTANCE;

	@Override
	public boolean isSuccessful() {
		return false;
	}

	@Override
	public boolean isValid() {
		return false;
	}

}
