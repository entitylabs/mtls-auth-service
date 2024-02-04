package org.entitylabs.mtls.authentication.filter;

import java.io.IOException;

import org.entitylabs.mtls.authentication.constants.ExceptionConstants;
import org.entitylabs.mtls.authentication.constants.LoggingConstants;
import org.entitylabs.mtls.authentication.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAuthorizationFailueFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			chain.doFilter(request, response);

		} catch (UserNotFoundException exception) {

			log.error(LoggingConstants.USER_AUTHORIZATION_FAILUE_FILTER_DO_FILTER_EXCEPTION, exception);

			response.getWriter().write(ExceptionConstants.USER_NOT_AUTHORIZED);

			((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

}
