package study.boardProject.auth.service;

import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AuthService {
    void signup(SignupRequest signupRequest);

    Map<String, String> login(LoginRequest loginRequest, HttpServletResponse response);

    TokenResponse reissue(TokenRequest tokenRequest);
}
