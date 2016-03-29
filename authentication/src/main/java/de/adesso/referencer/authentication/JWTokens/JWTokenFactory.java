/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.adesso.referencer.authentication.JWTokens;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class JWTokenFactory {

    private static String signingKeyJSON = "{\"key\":[-125,-100,-16,-31,-122,114,77,-115,52,31,107,15,-39,70,47,-48,-67,39,10,-128,41,118,39,-114,0,74,59,-83,-79,88,-11,-68,-23,65,-72,-54,101,-110,-47,46,52,40,85,-117,79,43,91,127,-116,21,-44,-12,61,68,-27,-65,-47,-11,-34,49,-107,42,0,70],\"algorithm\":\"HmacSHA512\"}";

    //The JWT signature algorithm we will be using to sign the token
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private static Gson gson = new Gson();

    private static long ttlMillis = 30 * 60 * 1000;

    private static Key getKeyFromJSON(String signingKeyJSON) {
        return gson.fromJson(signingKeyJSON, (MacProvider.generateKey()).getClass());
    }

    private static Key getKeyFromJSON() {
        return getKeyFromJSON(signingKeyJSON);
    }

    public static String getTokenForSubjectWithRole(String subject, String role) {
        String id = null;
        String issuer = "JWTokenFactory";
        Key key = getKeyFromJSON(signingKeyJSON);
        return createJWT(id, issuer, subject, role, ttlMillis, key, signatureAlgorithm);
    }

    public static String getTokenForSubjectWithRole(String subject, String role, long ttlMillis) {
        String id = null;
        String issuer = "JWTokenFactory";
        Key key = getKeyFromJSON(signingKeyJSON);
        return createJWT(id, issuer, subject, role, ttlMillis, key, signatureAlgorithm);
    }

    private static Key generateKey() {
        return MacProvider.generateKey();
    }

    private static String createJWT(String id,
            String issuer,
            String subject,
            String audience,
            long ttlMillis,
            Key key,
            SignatureAlgorithm signatureAlgorithm) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, key);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private static String createJWT(JWToken token,
            long ttlMillis,
            Key key,
            SignatureAlgorithm signatureAlgorithm) {
        String id = token.id;
        String issuer = token.issuer;
        String subject = token.subject;
        String audience = token.audience;
        return createJWT(id, issuer, subject, audience, ttlMillis, key, signatureAlgorithm);
    }

    private static JWToken readJWTokenFromString(String tokenString, Key key) {
        JWToken result = new JWToken();

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenString);
            Claims claims = jws.getBody();

            result.id = claims.getId();
            result.subject = claims.getSubject();
            result.audience = claims.getAudience();
            result.issuer = claims.getIssuer();
            if (claims.getIssuedAt() != null) {
                result.createdMillis = claims.getIssuedAt().getTime();
            }
            if (claims.getExpiration() != null) {
                result.expiredMillis = claims.getExpiration().getTime();
            }
            result.setIsValid(true);
        } catch (Exception e) {
            result.setIsValid(false);
        }
        return result;
    }

    public static JWToken readJWTokenFromString(String tokenString) {
        return readJWTokenFromString(tokenString, getKeyFromJSON());
    }

    private static String extendToken(String tokenString, Key key, long timeToExtend, SignatureAlgorithm signatureAlgorithm) {
        String result = tokenString;
        JWToken token;
        long timeNow = (new Date()).getTime();
        token = readJWTokenFromString(tokenString, key);
        if (timeNow > token.expiredMillis) {
            return result;
        }
        result = createJWT(token, timeToExtend, key, signatureAlgorithm);

        return result;
    }

    public static String extendToken(String tokenString) {
        return extendToken(tokenString, getKeyFromJSON(), ttlMillis, signatureAlgorithm);
    }
}
