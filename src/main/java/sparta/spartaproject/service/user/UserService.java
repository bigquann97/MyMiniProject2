package sparta.spartaproject.service.user;

import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public void signup(SignupRequest signupRequest);
    public void login(LoginRequest loginRequest, HttpServletResponse response);

}
