package br.com.ecocalc.security.jwt;

import java.util.function.Predicate;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ecocalc.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	//TODO: Not used, delete or use it.
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		parseJwt(request)
		.map(jwtUtils::getUserNameFromJwtToken)
		.map(userDetailsService::loadUserByUsername)
		.map(userDetails -> JWTPreAuthenticationToken
			.builder()
			.principal(userDetails)
			.details(new WebAuthenticationDetailsSource().buildDetails(request))
			.build())
		.ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
			filterChain.doFilter(request, response);
	}

	private Optional<String> parseJwt(HttpServletRequest request) {
		Predicate<String> empty = String::isEmpty;
    Predicate<String> notEmptyFilter = empty.negate();

		return Optional
				.ofNullable(request.getHeader("Authorization"))
            .filter(notEmptyFilter)
            .map(Pattern.compile("^Bearer (.+?)$")::matcher)
            .filter(Matcher::find)
            .map(matcher -> matcher.group(1));
	}
}
