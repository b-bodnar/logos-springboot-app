package ua.bohdan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.bohdan.repository.UserRepository;
import ua.bohdan.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email [" + email + "] not found")
                );

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(getAuthorities(userEntity))
                .build();
    }

    // ROLE_ADMIN, ROLE_USER - ADMIN, USER
    private List<SimpleGrantedAuthority> getAuthorities(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(r -> authorities.add(
                new SimpleGrantedAuthority("ROLE_" + r.getRole())));
        return authorities;
    }
}
