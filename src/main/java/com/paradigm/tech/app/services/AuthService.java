package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entity.User;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSigninDTO;
import com.paradigm.tech.app.model.entityDTO.response.ResponseSignupDTO;
import com.paradigm.tech.app.model.entityDTO.request.*;

import java.util.List;

public interface AuthService {

    ResponseSignupDTO signup(RequestSignupDTO requestRegister);
    ResponseSigninDTO signin(RequestSigninDTO requestSigninDTO);
    List<User> getAllUser();

}
