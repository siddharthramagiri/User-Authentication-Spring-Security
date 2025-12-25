package com.authorize.userauthentication.security;

import com.authorize.userauthentication.dto.authDto.LoginResponseDto;
import com.authorize.userauthentication.dto.authDto.SignUpResponseDto;
import com.authorize.userauthentication.entity.User;
import com.authorize.userauthentication.entity.type.AuthProviderType;
import com.authorize.userauthentication.entity.type.RoleType;
import com.authorize.userauthentication.exceptionHandler.exceptions.UserAlreadyExistsException;
import com.authorize.userauthentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public ResponseEntity<SignUpResponseDto> signup(String username, String password) {
        User user = signUpInternal(username, password, null, AuthProviderType.EMAIL);
        return ResponseEntity.ok(new SignUpResponseDto(
                user.getId(), user.getUsername(), "User Signed Up Successfully")
        );
    }
    public User signUpInternal(String username, String password, String providerId, AuthProviderType authProviderType) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            throw new UserAlreadyExistsException("User with email " + username + " Already Exists", HttpStatus.CONFLICT);
        }
        user = User.builder()
                .username(username)
                .roles(Set.of(RoleType.USER))
                .providerId(providerId)
                .providerType(authProviderType)
                .build();

        if(authProviderType.equals(AuthProviderType.EMAIL)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);
    }

    public ResponseEntity<LoginResponseDto> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDto(token, user.getId(), user.getRoles().stream().map(Enum::name).collect(Collectors.toSet())));
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(String registrationId, OAuth2User oAuth2User) {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email =  oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null) { // both Email login and OAuth login wasn't existed before
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signUpInternal(username, null, providerId, providerType);
        } else if (user != null) { // Email login is not found =====> new Entry for OAuth2 login
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
                user.setUsername(email);
                userRepository.save(user);
            }
        } else {
            throw new BadCredentialsException("This email is already existed with Provider " + emailUser.getProviderType());
        }

        String token = authUtil.generateToken(user);
        log.info(token);
        LoginResponseDto response = new LoginResponseDto(
                token, user.getId(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toSet())
        );

        return ResponseEntity.ok(response);
    }
}
