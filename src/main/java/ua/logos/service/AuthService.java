package ua.logos.service;

import ua.logos.domain.request.SigninRequest;
import ua.logos.domain.request.SignupRequest;

public interface AuthService {

    void registerUser(SignupRequest request);

    String loginUser(SigninRequest request);

}
