package org.entitylabs.mtls.authentication.configuration;

import java.time.ZonedDateTime;
import java.util.Date;

import org.entitylabs.mtls.authentication.constants.AppConstants;
import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author goelsan
 *
 */
public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

	@Override
	public ZonedDateTime convert(Date date) {

		return date.toInstant().atZone(AppConstants.APPLICATION_ZONE_OFFSET);
	}
}