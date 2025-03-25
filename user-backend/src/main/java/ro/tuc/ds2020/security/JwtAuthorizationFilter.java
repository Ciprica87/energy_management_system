package ro.tuc.ds2020.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private JwtUtil jwtUtil;

    private CustomUserDetailsService customUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            logger.debug("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        logger.debug("JWT token found in request headers");
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication != null) {
            logger.debug("JWT token is valid, setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.debug("JWT token is invalid or expired");
        }

        filterChain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Extract the token part after "Bearer "
            logger.debug("Authorization token found: " + token);

            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.extractRoleFromToken(token); // Extract the role from the token

            if (username != null && role != null) {
                logger.debug("Token parsed successfully for user: " + username);

                // Create a list of GrantedAuthorities based on the role
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                // Now create an authentication token with the username and authorities
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            } else {
                logger.warn("Failed to parse username or role from token");
            }
        } else {
            logger.debug("No Authorization token found in request");
        }
        return null;
    }


}
