package upc.edu.pe.finanzas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret:#{null}}")
    private String jwtSecretEnv;

    @Value("${app.jwtExpirationMs:86400000}")
    private int jwtExpirationMs;

    private SecretKey secretKey;

    /**
     * Inicializar la clave secreta
     */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            if (jwtSecretEnv != null && !jwtSecretEnv.isEmpty() && jwtSecretEnv.length() >= 64) {
                // Usar clave del environment si existe y es lo suficientemente larga
                secretKey = Keys.hmacShaKeyFor(jwtSecretEnv.getBytes());
            } else {
                // Generar una clave segura si no existe o es muy corta
                secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                System.out.println("⚠️  JWT_SECRET generado automáticamente. Para producción, usa:");
                System.out.println("app.jwtSecret=" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            }
        }
        return secretKey;
    }

    /**
     * Generar token JWT
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Obtener username del token
     */
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.err.println("Error al obtener username del token: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validar token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verificar si el token está expirado
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            System.err.println("Error al verificar expiración: " + e.getMessage());
            return true;
        }
    }
}