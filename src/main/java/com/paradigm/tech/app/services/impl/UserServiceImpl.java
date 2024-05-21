package com.paradigm.tech.app.services.impl;

import com.paradigm.tech.app.model.entity.*;
import com.paradigm.tech.app.repository.*;
import com.paradigm.tech.app.services.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public App_User loadUserByUserId(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("invalid credential user"));
        return App_User.builder()
                .id(String.valueOf(user.getId()))
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getRole())
                .build();

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("invalid credential user"));
        return App_User.builder()
                .id(String.valueOf(user.getId()))
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getRole())
                .build();

    }
}
