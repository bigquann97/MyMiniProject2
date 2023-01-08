package study.boardProject.auth.service;

import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    void signup(SignupRequest signupRequest);

    TokenResponse login(LoginRequest loginRequest, HttpServletResponse response);

    TokenResponse reissue(TokenRequest tokenRequest);

}
