package sparta.spartaproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.LoginRequestDto;
import sparta.spartaproject.dto.SignupRequestDto;
import sparta.spartaproject.dto.SignupResponseDto;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.AlreadyExistUserException;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        Optional<User> findUser = userRepository.findUserByLoginId(signupRequestDto.getLoginId());

        if (findUser.isPresent())
            throw new AlreadyExistUserException();

        String encodedPw = passwordEncoder.encode(signupRequestDto.getLoginPw());
        signupRequestDto.changePw(encodedPw);
        User signupUser = User.of(signupRequestDto);
        User savedUser = userRepository.save(signupUser);
        return SignupResponseDto.of(savedUser);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Optional<User> findUser = userRepository.findUserByLoginId(loginRequestDto.getLoginId());
        User loginUser = findUser.orElseThrow(() -> new IllegalArgumentException("없는 유저"));
        if(! passwordEncoder.matches(loginRequestDto.getLoginPw(), loginUser.getLoginPw())) {
            throw new IllegalArgumentException("비밀번호 오류");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginUser.getLoginId(), loginUser.getRole()));
    }
}
