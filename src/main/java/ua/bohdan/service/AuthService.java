package ua.bohdan.service;

import ua.bohdan.domain.request.SigninRequest;
import ua.bohdan.domain.request.SignupRequest;

public interface AuthService {

    void registerUser(SignupRequest request);

    String loginUser(SigninRequest request);

}
