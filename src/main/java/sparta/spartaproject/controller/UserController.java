package sparta.spartaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.spartaproject.dto.LoginReq;
import sparta.spartaproject.dto.SignUpReq;
import sparta.spartaproject.dto.SignupRes;
import sparta.spartaproject.service.ResultService;
import sparta.spartaproject.service.UserService;
import sparta.spartaproject.service.result.Result;
import sparta.spartaproject.service.result.Status;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResultService resultService;

    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid SignUpReq signUpReq) {
        Result result;
        if (signUpReq.validatePw()) {
            SignupRes resultData = userService.signup(signUpReq);
            result = resultService.getSuccessDataResult(Status.S_USER_CREATED.getCode(), Status.S_USER_CREATED.getMsg(), resultData);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            result = resultService.getFailureResult(Status.F_USER_INVALID_PW.getCode(), Status.F_USER_INVALID_PW.getMsg());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginReq loginReq, HttpServletResponse response) {
        userService.login(loginReq, response);
        String data = response.getHeader("Authorization");
        Result result = resultService.getSuccessDataResult(Status.S_USER_LOGIN.getCode(), Status.S_USER_LOGIN.getMsg(), data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

}
