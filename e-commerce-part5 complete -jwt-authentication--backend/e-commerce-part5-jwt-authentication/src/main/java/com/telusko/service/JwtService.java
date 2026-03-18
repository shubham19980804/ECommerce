package com.telusko.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



//  JwtService is responsible for handling JWT (JSON Web Token) operations
//  such as token generation, extraction of claims, and token validation.
@Component
public class JwtService {


    // Secret key for signing the JWT.should be kept private.
    private static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n" + "";




      //Generates a JWT token for the given userName.

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }



//     Creates a signing key from the base64 encoded secret.
//      return A Key object for signing the JWT.

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }



//     Extracts the userName from the JWT token.
//      return The userName contained in the token.

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }




//   Extracts the expiration date from the JWT token.
//    return The expiration date of the token.

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }




//     Extracts a specific claim from the JWT token.
//      @param claimResolver A function to extract the claim.
//      return The value of the specified claim.


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }



//      Extracts all claims from the JWT token.
//      return Claims object containing all claims.

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }



//      Checks if the JWT token is expired.
//     @return True if the token is expired, false otherwise.
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }



//     Validates the JWT token against the UserDetails.
//     @return True if the token is valid, false otherwise.

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
