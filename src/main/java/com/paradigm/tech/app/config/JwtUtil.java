package com.paradigm.tech.app.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.paradigm.tech.app.model.entity.App_User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
   private final String jwtSecret="paradigm";
   private final String appName = "paradigm";
   private final long jwtExpiration = 86400;

    public String generateToken(App_User appUser) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withIssuer(appName)
                .withSubject(appUser.getId())
                .withExpiresAt(Instant.now().plusSeconds(jwtExpiration))
                .withIssuedAt(Instant.now())
                .withClaim("role",appUser.getRole().name())
                .sign(algorithm);
    }

    public boolean verifyJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return  decodedJWT.getIssuer().equals(appName);

    }

    public Map<String, String> getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userinfo = new HashMap<>();
            userinfo.put("userId", decodedJWT.getSubject());
            userinfo.put("role", decodedJWT.getClaim("role").asString());

            return userinfo;
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }

    }
}
