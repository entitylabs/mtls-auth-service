package org.entitylabs.mtls.authentication.domain;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "functionalUsers")
public class FunctionalUser {

	@Id
	private String id;

	@NotBlank
	private String username;

	private List<String> roles;

	private ZonedDateTime createdOn;

	private ZonedDateTime lastModifiedOn;

	private Boolean isActive;
}
