package org.acme;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class JWTUtil {

    public static String generateToken(User user) {
        // Secret key for signing the token
        String secretKey = "8wJZTn9FgCmb1kR9k7eETxZqY76zN1m5T24uP1WcPlE="; // Make sure to secure this key properly


        // Set token expiration time (1 hour)
        long expirationTimeInMillis = 3600 * 1000; // 3600 seconds converted to milliseconds
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);

        // Create claims map
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("upn", user.getUsername());
        claims.put("groups", Set.of("user")); // You can add user roles here

        // Generate the JWT token
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("https://example.com/issuer")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey) // Use your chosen signing algorithm
                .compact();
    }
}
