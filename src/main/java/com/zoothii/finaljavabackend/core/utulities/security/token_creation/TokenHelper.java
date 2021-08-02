//package com.zoothii.finaljavabackend.core.utulities.security.token_creation;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class TokenHelper {
//    @Value("${app.name}")
//    private String APP_NAME;
//
//    @Value("${jwt.secret}")
//    private String SECRET;
//
//    @Value("${jwt.expires_in}")
//    private int EXPIRES_IN;
//
//
//    public String createJwtSecurityToken(String username) {
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
//
//        JwtBuilder builder = Jwts.builder()
//                .setId(username)
//                .setIssuedAt(now)
//                .setSubject(username)
//                .setIssuer(APP_NAME)
//                .signWith(SignatureAlgorithm.HS512, signingKey)
//                .setExpiration(generateExpirationDate());
//
//        return builder.compact();
//    }
//
//    public String getUsernameFromToken(String token) {
//        String username;
//        try {
//            final Claims claims = this.getClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
//    }
//
//    public Claims getClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            claims = null;
//        }
//        return claims;
//    }
//
//    private Date generateExpirationDate() {
//        return new Date(System.currentTimeMillis() + this.EXPIRES_IN);
//    }
//}
//
//
//
