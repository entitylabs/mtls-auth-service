package org.entitylabs.mtls.authentication.service.user;

import org.entitylabs.mtls.authentication.constants.ExceptionConstants;
import org.entitylabs.mtls.authentication.domain.FunctionalUser;
import org.entitylabs.mtls.authentication.exception.UserNotFoundException;
import org.entitylabs.mtls.authentication.repository.FunctionalUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final FunctionalUserRepository functionalUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("UserDetailServiceImpl.loadUserByUsername called with username {}", username);

		return functionalUserRepository.findByUsername(username).filter(user -> chekIsUserActive(username, user))

				.map(functionalUser -> User.builder().username(functionalUser.getUsername())
						.roles(functionalUser.getRoles().get(0)).password("").build())

				.orElseThrow(() -> {

					log.error("UserDetailServiceImpl.loadUserByUsername {} does not exist", username);

					throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND);
				});
	}

	private boolean chekIsUserActive(String username, FunctionalUser user) {
		
		log.info("UserDetailServiceImpl.loadUserByUsername user {} isActive ? {}", username, user.getIsActive());

		return user.getIsActive();
	}

}
