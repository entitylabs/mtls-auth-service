package org.entitylabs.mtls.authentication.service.jwt;

import java.security.Principal;

import org.entitylabs.mtls.authentication.dto.JwtTokenDTO;

public sealed interface UserAuthorizationService permits UserAuthorizationServiceImpl {

	JwtTokenDTO getToken(final Principal userPrincipal);
}
