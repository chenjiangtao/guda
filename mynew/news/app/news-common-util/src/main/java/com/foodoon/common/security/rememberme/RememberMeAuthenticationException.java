package com.foodoon.common.security.rememberme;

import com.foodoon.common.security.AuthenticationException;

@SuppressWarnings("serial")
public class RememberMeAuthenticationException extends AuthenticationException {
	public RememberMeAuthenticationException() {
	}

	public RememberMeAuthenticationException(String msg) {
		super(msg);
	}
}
