package study.boardProject.auth.service;

import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    void signup(SignupRequest signupRequest);
    void login(LoginRequest loginRequest, HttpServletResponse response);
    TokenResponse reissue(TokenRequest tokenRequest);
}
