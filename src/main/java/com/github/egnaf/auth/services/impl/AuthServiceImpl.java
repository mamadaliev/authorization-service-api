package com.github.egnaf.auth.services.impl;

import com.github.egnaf.auth.exceptions.AuthenticationException;
import com.github.egnaf.auth.exceptions.NotFoundException;
import com.github.egnaf.auth.repositories.UserRepository;
import com.github.egnaf.auth.transfers.TokensTransfer;
import com.github.egnaf.auth.transfers.forms.LoginForm;
import com.github.egnaf.auth.transfers.forms.RegisterForm;
import com.github.egnaf.auth.configs.tokens.TokenProvider;
import com.github.egnaf.auth.models.RoleModel;
import com.github.egnaf.auth.models.UserModel;
import com.github.egnaf.auth.services.AuthService;
import com.github.egnaf.auth.utils.RandomIdentifier;
import com.github.egnaf.auth.utils.Status;
import com.github.egnaf.auth.utils.TimestampHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokensTransfer register(RegisterForm registerForm) {
        // checking that the user exists in the database
        if (!userRepository.existsByUsername(registerForm.getUsername())) {

            // create a new refresh token
            String refreshToken = tokenProvider.createRefreshToken();

            // create instance of user model and fill it
            UserModel userModel = UserModel.builder()
                    .id(RandomIdentifier.generate(registerForm.getUsername()))
                    .username(registerForm.getUsername())
                    .email(registerForm.getEmail())
                    .password(passwordEncoder.encode(registerForm.getPassword()))
                    .refreshToken(refreshToken)
                    .roles(new HashSet<>())
                    .status(Status.ACTIVE)
                    .created(TimestampHelper.getCurrentTimestamp())
                    .updated(TimestampHelper.getCurrentTimestamp())
                    .lastVisit(TimestampHelper.getCurrentTimestamp())
                    .build();

            // add roles to instance of user model
            userModel.getRoles().add(new RoleModel("ROLE_USER"));

            // save the user in database
            userRepository.save(userModel);

            // create tokens transfer object and return it
            return TokensTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(userModel.getUsername(), userModel.getRoles()))
                    .refreshToken(refreshToken)
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } else {
            throw new AuthenticationException("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public TokensTransfer login(LoginForm loginForm) {
        try {
            // find user from database
            UserModel userModel = userRepository.findByUsername(loginForm.getUsername()).orElseThrow(() ->
                            new NotFoundException("The user by username [" + loginForm.getUsername() + "] not found",
                                    HttpStatus.NOT_FOUND));

            // create a new refresh token
            String refreshToken = tokenProvider.createRefreshToken();

            // update refresh token
            userModel.setRefreshToken(refreshToken);

            // update last visit date
            userModel.setLastVisit(TimestampHelper.getCurrentTimestamp());

            // update user details in database
            userRepository.save(userModel);

            // authenticate this user in the authentication manager
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),
                    loginForm.getPassword()));

            // create tokens transfer object and return it
            return TokensTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(userModel.getUsername(), userModel.getRoles()))
                    .refreshToken(refreshToken)
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserModel whoami(HttpServletRequest request) {
        // get username from jwt token
        String username = tokenProvider.getUsernameByToken(tokenProvider.extractToken(request));

        // find user by username from database
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("The user by username [" + username + "] not found",
                        HttpStatus.NOT_FOUND));

        // update last visit date
        userModel.setLastVisit(TimestampHelper.getCurrentTimestamp());

        return userModel;
    }

    @Override
    public TokensTransfer refresh(String username, String refreshToken) {
        // get instance of user model by username from database
        Optional<UserModel> userModel = userRepository.findByUsername(username);

        // check for equality of refresh tokens
        if (userModel.isPresent() && userModel.get().getRefreshToken().equals(refreshToken)) {

            // update last visit date
            userModel.get().setLastVisit(TimestampHelper.getCurrentTimestamp());

            return TokensTransfer.builder()
                    .accessToken(tokenProvider.createAccessToken(username, userModel.get().getRoles()))
                    .refreshToken(tokenProvider.createRefreshToken())
                    .tokenType(tokenProvider.getTokenType())
                    .expiresIn(tokenProvider.getExpire())
                    .build();
        } else {
            throw new AuthenticationException("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }
    }
}
