package org.entitylabs.mtls.authentication.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DatabaseConfiguration {

	@Bean
	MongoCustomConversions mongoCustomConversions() {

		log.info("DatabaseConfiguration.mongoCustomConversions:.called");

		return new MongoCustomConversions(
				Arrays.asList(new ZonedDateTimeReadConverter(), new ZonedDateTimeWriteConverter()));
	}
}
