package org.entitylabs.mtls.authentication.controller;

import java.security.Principal;

import org.entitylabs.mtls.authentication.dto.JwtTokenDTO;

sealed interface UserAuthorizationController permits UserAuthorizationControllerImpl {

	JwtTokenDTO getToken(Principal principal);

}
