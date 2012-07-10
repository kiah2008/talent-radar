package com.menatwork.service;

import org.json.JSONObject;

public enum ServiceCall {
	LOGIN {
		@Override
		Class<? extends Response> getWrapperClass() {
			return LoginResponse.class;
		}
	}, REGISTER {
		@Override
		Class<? extends Response> getWrapperClass() {
			return RegisterResponse.class;
		}
	}

	;

	abstract Class<? extends Response> getWrapperClass();

	@SuppressWarnings("unchecked")
	public <T extends Response> T wrap(JSONObject response) {
		try {
			return (T) this.getWrapperClass().getConstructor(JSONObject.class)
					.newInstance(response);
		} catch (Exception
		/*
		 * IllegalArgumentException, SecurityException, InstantiationException,
		 * IllegalAccessException, InvocationTargetException,
		 * NoSuchMethodException
		 * 
		 * Seriously bro, safe code.
		 *///
		e) {
			throw new RuntimeException(e);
		}
	}

}
