package sparta.spartaproject.service.user;

import sparta.spartaproject.dto.token.TokenRequest;
import sparta.spartaproject.dto.token.TokenResponse;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    void signup(SignupRequest signupRequest);
    void login(LoginRequest loginRequest, HttpServletResponse response);
    TokenResponse reissue(TokenRequest tokenRequest);
}
