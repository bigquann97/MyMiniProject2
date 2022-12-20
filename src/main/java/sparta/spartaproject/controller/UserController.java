package sparta.spartaproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.token.TokenResponse;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.dto.user.SignupResponse;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.ResultService;
import sparta.spartaproject.result.Status;
import sparta.spartaproject.service.UserService;

import javax.validation.Valid;

@Api(value = "회원가입, 로그인", tags = "회원가입, 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResultService resultService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Result signup(@RequestBody @Valid SignupRequest signupRequest) {
        SignupResponse data = userService.signup(signupRequest);
        return resultService.getSuccessDataResult(Status.S_USER_CREATED, data);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Result login(@RequestBody LoginRequest loginRequest) {
        TokenResponse data = userService.login(loginRequest);
        return resultService.getSuccessDataResult(Status.S_USER_LOGIN, data);
    }

}
