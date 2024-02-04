package org.entitylabs.mtls.authentication.security;

import org.entitylabs.mtls.authentication.filter.UserAuthorizationFailueFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurifyConfiguration {

	private final UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.x509(secutityCustomizer -> secutityCustomizer.userDetailsService(userDetailsService))
				.addFilterBefore(new UserAuthorizationFailueFilter(), LogoutFilter.class)

				.build();

	}

}
