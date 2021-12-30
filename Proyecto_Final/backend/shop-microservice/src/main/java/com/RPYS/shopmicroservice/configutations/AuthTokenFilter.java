package com.RPYS.shopmicroservice.configutations;
import com.RPYS.shopmicroservice.repositories.UserClient;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";


    String SECRET = "$2a$10$.yt/XlwhFoTvvXLzDOH4mOs.gPwK92ZeWpcQK2uhjFlqIdbINtv6O";
    private final UserClient userClient;

    public AuthTokenFilter(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = parseJwt(request);
            if (jwt != null && validateJwtToken(jwt)) {
                Claims claims = getTokenBody(request);
                setUpSpringAuthentication(claims);
                filterChain.doFilter(request, response);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sin permisos");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sin permisos");
        }

        //filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private Claims getTokenBody(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken).getBody();
        return claims;
    }

    public boolean validateJwtToken(String authToken) throws SignatureException {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}",e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e);
        }

        return false;
    }

    private void setUpSpringAuthentication(Claims claims) {

        List authorities = (List) claims.get("roles");
        List<SimpleGrantedAuthority> listaAuto = new ArrayList<>();
        authorities.stream().forEach( role -> {
            listaAuto.add(new SimpleGrantedAuthority((String) role));

        } );

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,listaAuto);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

}

