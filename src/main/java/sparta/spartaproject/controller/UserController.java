package sparta.spartaproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.token.TokenRequest;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.service.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "USER API", tags = "USER API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest signupRequest) {
        userService.signup(signupRequest);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        userService.login(loginRequest, response);
    }

    // TODO: 2022/12/31 Access 토큰 재발급 / refreshToken 체크 메서드
    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급 요청")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    public void reIssue(@RequestBody TokenRequest tokenRequest) {
        userService.reissue(tokenRequest);
    }
}
