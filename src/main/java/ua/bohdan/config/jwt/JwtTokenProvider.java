package ua.bohdan.config.jwt;


import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.bohdan.config.AppConstants;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date validDate = new Date(now.getTime() + AppConstants.ACCESS_TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AppConstants.AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, AppConstants.SECRET_KEY)
                .setIssuedAt(now)
                .setExpiration(validDate)
                .compact();


    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(AppConstants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername())
                && !isTokenExpired(token)
        );
    }

    public UsernamePasswordAuthenticationToken getAuthentication(
            String token, Authentication authentication, UserDetails userDetails
    ) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(AppConstants.SECRET_KEY);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AppConstants.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

    }

}
