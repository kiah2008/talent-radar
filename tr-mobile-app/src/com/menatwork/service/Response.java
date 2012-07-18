package com.menatwork.service;

public interface Response {

	/**
	 * Default behavior for determining if a request is successful implies that
	 * the 'status' field in the 'result' object of the returned JSON is "ok"
	 * 
	 * @return
	 */
	public abstract boolean isSuccessful();

	/**
	 * Checks wether the response indicates the service call was valid (correct
	 * parameters). An false response may indicate a malformed request (wrong
	 * post parameters, invalid input, etc).
	 * 
	 * @return
	 */
	public abstract boolean isValid();
}