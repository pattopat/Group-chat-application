package com.tinyblu.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tinyblu.backend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.token.secret}")

    private String secret;
    @Value("${app.token.issuer}")
    private String issuer;

    public String tokenize(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    // specify a specific claim validations
                    .withIssuer(issuer)
                    // reusable verifier instance
                    .build();

            return verifier.verify(token);

        } catch (Exception e) {
            return null;
        }


    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
}
