package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.logos.domain.request.SigninRequest;
import ua.logos.domain.request.SignupRequest;
import ua.logos.domain.response.SigninResponse;
import ua.logos.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest request) {
        authService.registerUser(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody SigninRequest signinRequest) {
        String token = authService.loginUser(signinRequest);
        return new ResponseEntity(SigninResponse.builder().token(token).build(), HttpStatus.OK);
    }

}
