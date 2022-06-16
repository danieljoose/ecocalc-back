package br.com.ecocalc.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.ecocalc.domains.Usuario;
import br.com.ecocalc.security.services.UserDetailsImpl;
import br.com.ecocalc.services.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${jwt.secret64}")
	private String jwtSecret;

	@Value("${jwt.jwtExpirationMs}")
	private int jwtExpirationMs;

    @Autowired
    private UsuarioService usuarioService;

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Usuario usuario = usuarioService.findById(userPrincipal.getId()).get();

		return Jwts.builder()
            .setSubject(usuario.getEmail())
            .claim("id", usuario.getId())
            .claim("email", usuario.getEmail())
			.claim("nome", usuario.getNome() + ' ' + usuario.getSobrenome())
            // .claim("emailValidado", usuario.getEmailValidado())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
	}

    public String generateJwtToken(Usuario usuario) {
		return Jwts.builder()
				.setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("email", usuario.getEmail())
				.claim("nome", usuario.getNome() + ' ' + usuario.getSobrenome())
                // .claim("emailValidado", usuario.getEmailValidado())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}