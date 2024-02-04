package org.entitylabs.mtls.authentication.service.jwt;

import java.security.Principal;

import org.entitylabs.mtls.authentication.constants.ExceptionConstants;
import org.entitylabs.mtls.authentication.constants.LoggingConstants;
import org.entitylabs.mtls.authentication.dto.JwtTokenDTO;
import org.entitylabs.mtls.authentication.exception.UnauthorizedException;
import org.entitylabs.mtls.authentication.repository.FunctionalUserRepository;
import org.entitylabs.mtls.authentication.util.JwtUtil;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
final class UserAuthorizationServiceImpl implements UserAuthorizationService {

	private final JwtUtil jwtUtil;

	private final FunctionalUserRepository fuuncFunctionalUserRepository;

	public JwtTokenDTO getToken(final Principal userPrincipal) {
		
		log.info(LoggingConstants.USER_AUTHORIZATION_SERVICE_IMPL_GET_TOKEN_CALLED);

		return fuuncFunctionalUserRepository.findByUsername(userPrincipal.getName())
				.map(user -> jwtUtil.generateToken(user)).map(token -> JwtTokenDTO.builder().token(token).build())
				.orElseThrow(() -> new UnauthorizedException(ExceptionConstants.USER_NOT_AUTHORIZED));

	}

}
