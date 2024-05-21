package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.config.JwtUtil;
import com.paradigm.tech.app.model.entity.App_User;
import com.paradigm.tech.app.model.entity.Role;
import com.paradigm.tech.app.model.entity.User;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSigninDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSignupDTO;
import com.paradigm.tech.app.repository.UserRepository;
import com.paradigm.tech.app.services.AuthService;
import com.paradigm.tech.app.utlls.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.paradigm.tech.app.model.entityDTO.request.*;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseSignupDTO signup(RequestSignupDTO requestRegister) {
        Instant timestamp = Instant.now();
        User user = User.builder()
                .email(requestRegister.getEmail())
                .password(passwordEncoder.encode(requestRegister.getPassword()))
                .createdAt(timestamp)
                .role(Role.builder()
                        .role(ERole.ADMIN)
                        .build())
                .build();
        userRepository.save(user);
        return ResponseSignupDTO.builder()
                .email(user.getEmail())
                .role(user.getRole().getRole())
                .build();
    }

    @Override
    public ResponseSigninDTO signin(RequestSigninDTO requestSigninDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestSigninDTO.getEmail(),
                            requestSigninDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            App_User appUser = (App_User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(appUser);

            return ResponseSigninDTO.builder()
                    .email(requestSigninDTO.getEmail())
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during signin", e);
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
