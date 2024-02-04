package org.entitylabs.mtls.authentication.exception.handler;

import org.entitylabs.mtls.authentication.constants.ExceptionConstants;
import org.entitylabs.mtls.authentication.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthenticationExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResponseEntity<String> handleUserNotAuthorizedException(UnauthorizedException exception) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionConstants.USER_NOT_AUTHORIZED);
	}
}
