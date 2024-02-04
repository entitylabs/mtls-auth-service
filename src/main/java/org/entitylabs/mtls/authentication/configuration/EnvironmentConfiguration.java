package org.entitylabs.mtls.authentication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class EnvironmentConfiguration {

	@Value("${service.token-timeout-ms}")
	@NotNull
	private Long tokenTimeoutMs;

	@Value("${service.token-secret}")
	@NotBlank
	private String tokenSecret;

}
