package taass.bibliotech.apigateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import taass.bibliotech.apigateway.filter.PreFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        log.error("----0) REQUEST new : " + request.getHeader("Authorization"));

        String header = request.getHeader(jwtConfig.getHeader());

        log.error("1) HEADER: " + header);

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            log.error("NOT VALID TOKEN 2)");
            return;
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        // 3. Get the token
        String token = header.replace(jwtConfig.getPrefix(), "");
        log.error("3) TOKEN: " + token);
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("IHJg7INqdWUGhCsfZFG/g0X28Q5qxy5DA5Y+CbB2+jI=}")
                    .parseClaimsJws(token)
                    .getBody();

            log.error("4) CLAIMS: " + claims.toString());

            String username = claims.getSubject();

            log.error("5) USERNAMAE: " + claims.getSubject().toString());
            if(username != null) {
                @SuppressWarnings("unchecked")

                List<String> authorities = claims.get("authorities", List.class);
                log.error("6) authorities: " + claims.getSubject().toString());
                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // 6. Authenticate the user
                // Now, user is authenticated
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            log.error("5) ERROR msg: " + e.getMessage());
            log.error("5) ERROR cause: " + e.getCause());
            SecurityContextHolder.clearContext();
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response);
    }

}
