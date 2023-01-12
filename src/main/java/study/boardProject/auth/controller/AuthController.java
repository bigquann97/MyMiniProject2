package study.boardProject.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;
import study.boardProject.auth.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(value = "USER API", tags = "USER API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest signupRequest) {
        authService.signup(signupRequest);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest, response);
    }

    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급 요청")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/re-issue")
    public TokenResponse reissue(@RequestBody TokenRequest tokenRequest, HttpServletResponse response) {
        return authService.reissue(tokenRequest, response);
    }

}
