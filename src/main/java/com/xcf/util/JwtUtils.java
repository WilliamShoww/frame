package com.xcf.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

public class JwtUtils {

    public static String create(String jsonPayload, String securityKey) {
        String compactJws = Jwts.builder()
                .setPayload(jsonPayload)
                .signWith(SignatureAlgorithm.HS512, securityKey)
                .compact();
        return compactJws;
    }

    public static Map<String, Object> parse(String token, String securityKey) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }
}
