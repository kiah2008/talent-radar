package com.menatwork.service;

public interface Response {

	/**
	 * Default behavior for determining if a request is successful implies that
	 * the 'status' field in the 'result' object of the returned JSON is "ok"
	 * 
	 * @return
	 */
	public abstract boolean isSuccessful();

}