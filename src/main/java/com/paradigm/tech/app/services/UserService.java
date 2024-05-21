package com.paradigm.tech.app.services;

import com.paradigm.tech.app.model.entity.*;
import org.springframework.security.core.userdetails.*;

public interface UserService extends UserDetailsService {
    App_User loadUserByUserId(String id);
}
