package upc.service.taller.alumno.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import upc.service.taller.alumno.entity.UsuarioEntity;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String TOKEN_TYPE_CLAIM = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expiration-ms}") long accessTokenExpirationMs,
            @Value("${security.jwt.refresh-token-expiration-ms}") long refreshTokenExpirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public String generarAccessToken(UsuarioEntity usuario) {
        Map<String, Object> claims = crearClaims(usuario, ACCESS_TOKEN_TYPE);
        return generarToken(claims, usuario.getUserName(), accessTokenExpirationMs);
    }

    public String generarRefreshToken(UsuarioEntity usuario) {
        Map<String, Object> claims = crearClaims(usuario, REFRESH_TOKEN_TYPE);
        return generarToken(claims, usuario.getUserName(), refreshTokenExpirationMs);
    }

    public String obtenerUserName(String token) {
        return obtenerClaims(token).getSubject();
    }

    public boolean esAccessToken(String token) {
        return ACCESS_TOKEN_TYPE.equals(obtenerClaims(token).get(TOKEN_TYPE_CLAIM, String.class));
    }

    public boolean esRefreshToken(String token) {
        return REFRESH_TOKEN_TYPE.equals(obtenerClaims(token).get(TOKEN_TYPE_CLAIM, String.class));
    }

    public boolean tokenValido(String token, String userName) {
        String tokenUserName = obtenerUserName(token);
        return tokenUserName.equals(userName) && !estaExpirado(token);
    }

    private String generarToken(Map<String, Object> claims, String subject, long expirationMs) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationMs);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(secretKey)
                .compact();
    }

    private Map<String, Object> crearClaims(UsuarioEntity usuario, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, tokenType);
        claims.put("idUsuario", usuario.getIdUsuario());
        if (usuario.getPersona() != null) {
            claims.put("idPersona", usuario.getPersona().getIdPersona());
        }
        claims.put("roles", "ROLE_USER");
        return claims;
    }

    private boolean estaExpirado(String token) {
        return obtenerClaims(token).getExpiration().before(new Date());
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
