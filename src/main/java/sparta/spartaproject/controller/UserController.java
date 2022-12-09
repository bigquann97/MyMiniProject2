package sparta.spartaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.spartaproject.dto.LoginRequestDto;
import sparta.spartaproject.dto.SignupRequestDto;
import sparta.spartaproject.dto.SignupResponseDto;
import sparta.spartaproject.service.ResponseService;
import sparta.spartaproject.service.UserService;
import sparta.spartaproject.service.result.Result;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        Result result;
        if (signupRequestDto.validatePw()) {
            SignupResponseDto resultData = userService.signup(signupRequestDto);
            result = responseService.getSuccessDataResult(100, "유저 생성 완료", resultData);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            result = responseService.getFailureResult(-100, "비밀번호 재입력 오류");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        Result result = responseService.getSuccessResult(101, "정상 로그인");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

}
