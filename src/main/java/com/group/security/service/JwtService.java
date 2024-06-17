package com.group.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String SECRET = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF"; // Defining the secret key for signing the JWT.

    // Method to generate a JWT token with the given claims.
    public String generateToken(String id, String role, String first_name, String username, String email, String user_id) {
        Map<String, Object> claims = new HashMap<>(); // Creating a claims map.
        claims.put("id", id); // Adding the user ID claim.
        claims.put("role", role); // Adding the user role claim.
        claims.put("first_name", first_name); // Adding the username claim.
        claims.put("user_id",user_id);
        claims.put("email", email); // Adding the email claim.
        return createToken(claims, username); // Creating the token with claims and subject.
    }

    // Method to create a token with specified claims and subject.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder() // Building the JWT.
                .setClaims(claims) // Setting the claims.
                .setSubject(subject) // Setting the subject (username).
                .setIssuedAt(new Date(System.currentTimeMillis())) // Setting the issue date.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Setting the expiration date (30 minutes from now).
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Signing the JWT with the secret key and HS256 algorithm.
                .compact(); // Compacting the JWT into a string.
    }

    // Method to decode the secret key and return it as a Key object.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); // Decoding the secret key from Base64.
        return Keys.hmacShaKeyFor(keyBytes); // Returning the decoded key as an HMAC SHA-256 key.
    }

    // Method to extract the username (subject) from the JWT token.
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject); // Extracting the subject claim from the token.
    }

    // Method to extract the expiration date from the JWT token.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extracting the expiration claim from the token.
    }

    // Generic method to extract a specific claim from the JWT token using a claim resolver function.
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token); // Extracting all claims from the token.
        return claimResolver.apply(claims); // Applying the claim resolver function to extract the desired claim.
    }

    // Method to parse the JWT token and extract all claims using the signing key.
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder() // Creating a JWT parser.
                    .setSigningKey(getSignKey()) // Setting the signing key.
                    .build() // Building the parser.
                    .parseClaimsJws(token) // Parsing the token to extract claims.
                    .getBody(); // Getting the claims body.
        } catch (ExpiredJwtException e) {
            // Handle the expired token exception
            throw new RuntimeException("JWT token has expired", e);
        }
    }

    // Method to check if the JWT token has expired.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Checking if the expiration date is before the current date.
    }

    // Method to validate the JWT token by checking if the username matches and if the token is not expired.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token); // Extracting the username from the token.
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Validating the token.
    }

    // Method to extract custom claims from the JWT token.
    public Map<String, Object> extractCustomClaims(String token) {
        final Claims claims = extractAllClaims(token); // Extracting all claims from the token.
        Map<String, Object> customClaims = new HashMap<>(); // Creating a map for custom claims.
        customClaims.put("id", claims.get("id")); // Adding the id claim.
        customClaims.put("role", claims.get("role")); // Adding the role claim.
        customClaims.put("name", claims.get("name")); // Adding the name claim.
        customClaims.put("user_id", claims.get("user"));
        customClaims.put("email", claims.get("email")); // Adding the email claim.
        return customClaims; // Returning the custom claims.
    }
}