package study.boardProject.service.auth;

import study.boardProject.dto.token.TokenRequest;
import study.boardProject.dto.token.TokenResponse;
import study.boardProject.dto.user.LoginRequest;
import study.boardProject.dto.user.SignupRequest;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    void signup(SignupRequest signupRequest);
    void login(LoginRequest loginRequest, HttpServletResponse response);
    TokenResponse reissue(TokenRequest tokenRequest);
}
