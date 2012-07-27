package com.menatwork.service;

public class Defect extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Defect(final String message) {
		super(message);
	}

}
