package ua.bohdan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.bohdan.exceptions.AlreadyExistsException;
import ua.bohdan.exceptions.NotFoundException;
import ua.bohdan.repository.RoleRepository;
import ua.bohdan.repository.UserRepository;
import ua.bohdan.config.jwt.JwtTokenProvider;
import ua.bohdan.domain.request.SigninRequest;
import ua.bohdan.domain.request.SignupRequest;
import ua.bohdan.entity.RoleEntity;
import ua.bohdan.entity.UserEntity;
import ua.bohdan.service.AuthService;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerUser(SignupRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AlreadyExistsException("User with email [" + request.getEmail() + "] already exists");
        }

        String password = request.getPassword();
        System.out.println("Password: " + password);

        String encPassword = passwordEncoder.encode(password);
        System.out.println("Enc Password: " + encPassword);

        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(encPassword);
        userEntity.setDeleted(false);
        userEntity.setImage("logo.png");
        userEntity.setSexType(request.getSexType());

        RoleEntity roleEntity = roleRepository.findByRoleIgnoreCase("USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));
        userEntity.setRoles(Arrays.asList(roleEntity));

        userRepository.save(userEntity);
    }

    @Override
    public String loginUser(SigninRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
