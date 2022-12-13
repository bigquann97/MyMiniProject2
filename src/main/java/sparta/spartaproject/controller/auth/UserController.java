package sparta.spartaproject.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.user.LoginReq;
import sparta.spartaproject.dto.user.SignUpReq;
import sparta.spartaproject.dto.user.SignupRes;
import sparta.spartaproject.dto.token.TokenRes;
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.service.user.UserService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

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
    public Result signup(@RequestBody @Valid SignUpReq signUpReq) {
        SignupRes resultData = userService.signup(signUpReq);
        Result result = resultService.getSuccessDataResult(Status.S_USER_CREATED.getCode(), Status.S_USER_CREATED.getMsg(), resultData);
        return result;
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Result login(@RequestBody LoginReq loginReq) {
        TokenRes data = userService.login(loginReq);
        Result result = resultService.getSuccessDataResult(Status.S_USER_LOGIN.getCode(), Status.S_USER_LOGIN.getMsg(), data);
        return result;
    }

}
