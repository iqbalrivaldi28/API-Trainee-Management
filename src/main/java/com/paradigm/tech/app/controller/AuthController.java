package com.paradigm.tech.app.controller;

import com.paradigm.tech.app.model.entity.User;
import com.paradigm.tech.app.model.entityDTO.request.*;
import com.paradigm.tech.app.model.entityDTO.response.*;
import com.paradigm.tech.app.services.*;
import com.paradigm.tech.app.utlls.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSigninDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSignupDTO;
import com.paradigm.tech.app.services.AuthService;
import com.paradigm.tech.app.utlls.ApiPathConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CustomResponseDTO<?>> signup(@RequestBody RequestSignupDTO requestSignupDTO) {
        ResponseSignupDTO responseSignupDTO = authService.signup(requestSignupDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CustomResponseDTO<>(true,200, "Success created account!", responseSignupDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<CustomResponseDTO<?>> signin(@RequestBody RequestSigninDTO requestSigninDTO) {
        try {
            ResponseSigninDTO responseSigninDTO = authService.signin(requestSigninDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CustomResponseDTO<>(true, 200, "Success signin!", responseSigninDTO));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

            String errorMessage = e.getMessage().contains("Invalid username or password") ?
                    "Invalid username or password" : "An error occurred during signin";

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CustomResponseDTO<>(false, 401, errorMessage, null));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<CustomResponseDTO<?>> getAllUser() {
        List<User> users = authService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponseDTO<>(true, 200, "All users fetched", users));
    }
}
