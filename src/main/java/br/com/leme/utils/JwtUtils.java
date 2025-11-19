package br.com.leme.utils;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class JwtUtils {
    private static final String ISSUER;
    private static final int EXPIRATION;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        ISSUER = bundle.getString("mp.jwt.verify.issuer");
        EXPIRATION = Integer.parseInt(bundle.getString("jwt.expiration.seconds"));
    }

    public static String generateToken (
        String id,
        String nome,
        String username,
        String email
    ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nome", nome);
        claims.put("username", username);
        claims.put("email", email);

        JwtClaimsBuilder builder = Jwt.claims(claims)
                .subject(id)
                .issuer(ISSUER)
                .expiresIn(EXPIRATION);

        return builder.sign();
    }
}
