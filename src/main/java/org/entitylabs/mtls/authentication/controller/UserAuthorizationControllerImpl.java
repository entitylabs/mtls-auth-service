package org.entitylabs.mtls.authentication.controller;

import java.security.Principal;

import org.entitylabs.mtls.authentication.constants.LoggingConstants;
import org.entitylabs.mtls.authentication.dto.JwtTokenDTO;
import org.entitylabs.mtls.authentication.service.jwt.UserAuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public final class UserAuthorizationControllerImpl implements UserAuthorizationController {

	private final UserAuthorizationService userAuthorizationService;

	@GetMapping
	public JwtTokenDTO getToken(final Principal principal) {
		
		log.info(LoggingConstants.USER_AUTHORIZATION_CONTROLLER_IMPL_GET_TOKEN_CALLED);

		return userAuthorizationService.getToken(principal);

	}
}
