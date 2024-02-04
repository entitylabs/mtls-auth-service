package org.entitylabs.mtls.authentication.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.entitylabs.mtls.authentication.configuration.EnvironmentConfiguration;
import org.entitylabs.mtls.authentication.domain.FunctionalUser;
import org.entitylabs.mtls.authentication.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class JwtUtil {

	private final EnvironmentConfiguration environmentConfiguration;

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(FunctionalUser userDetails) {

		log.info("JWTUtil.generateToken called for user {}" + userDetails.getUsername());

		Map<String, Object> claims = new HashMap<>();
		List<String> grantedAuthorities = userDetails.getRoles().stream()
				.map(role -> role.substring("ROLE_".length()))
				.collect(Collectors.toList());

		claims.put("roles", grantedAuthorities);
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public String extractUserName(String token) {

		log.info("JWTUtil.extractUserName called");

		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public List<String> extractUserRoles(String token) {

		log.info("JWTUtil.extractUserRoles called");

		return extractRoles(token);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> extractRoles(String token) {

		log.info("JWTUtil.extractRoles called");

		var claims = extractAllClaims(token);

		if (claims.get("roles") instanceof List<?>) {
			return (List<String>) claims.get("roles");
		}
		return List.of();
	}

	/**
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		log.info("JWTUtil.extractClaim called");

		Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {

		log.info("JWTUtil.extractAllClaims called");

		return Jwts.parser().setSigningKey(environmentConfiguration.getTokenSecret()).parseClaimsJws(token).getBody();
	}

	/**
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		log.info("JWTUtil.createToken");

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + environmentConfiguration.getTokenTimeoutMs()))
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, environmentConfiguration.getTokenSecret())
				.compact();
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public Boolean validateToken(String token) {

		log.info("JWTUtil.validateToken called");

		try {

			Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(environmentConfiguration.getTokenSecret()))
					.parse(token).getBody();

		} catch (Exception exception) {

			log.error("JWTUtil.validateToken.exception unauthorized accessm {}", exception);

			throw new UnauthorizedException("Not Authorized");
		}
		if (isTokenExpired(token)) {

			log.error("JWTUtil.validateToken.exception: expired token");

			throw new UnauthorizedException("Token expired");
		}

		return true;

	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token) {

		log.info("JWTUtil::isTokenExpired::called");

		return extractExpiration(token).before(new Date());
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}
}
