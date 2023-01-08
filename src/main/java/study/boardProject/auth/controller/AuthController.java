package study.boardProject.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

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
    public Map<String, String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest, response);
    }

    // TODO: 2022/12/31 Access 토큰 재발급 / refreshToken 체크 메서드
    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급 요청")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    public void reissue(@RequestBody TokenRequest tokenRequest) {
        authService.reissue(tokenRequest);
    }
}
