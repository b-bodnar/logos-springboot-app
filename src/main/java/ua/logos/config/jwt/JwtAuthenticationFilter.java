package ua.logos.config.jwt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.logos.config.AppConstants.HEADER_STRING;
import static ua.logos.config.AppConstants.TOKENPREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String userName = null;
        String authToken = null;
        String header = httpServletRequest.getHeader(HEADER_STRING);

        if (header != null && header.startsWith(TOKENPREFIX)) {
            authToken = header.replace(TOKENPREFIX, "");

            userName = jwtTokenProvider.getUserNameFromToken(authToken);


        } else {
            System.out.println("Could not find Bearer token");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtTokenProvider.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        jwtTokenProvider.getAuthentication(authToken,
                                SecurityContextHolder.getContext().getAuthentication(), userDetails);

                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
