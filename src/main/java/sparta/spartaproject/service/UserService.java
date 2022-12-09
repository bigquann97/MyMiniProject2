package sparta.spartaproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.LoginReq;
import sparta.spartaproject.dto.SignUpReq;
import sparta.spartaproject.dto.SignupRes;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.AlreadyExistUserException;
import sparta.spartaproject.exception.WrongPwException;
import sparta.spartaproject.exception.NotExistUserException;
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
    public SignupRes signup(SignUpReq signUpReq) {
        Optional<User> findUser = userRepository.findUserByLoginId(signUpReq.getLoginId());
        if (findUser.isPresent())
            throw new AlreadyExistUserException();
        String encodedPw = passwordEncoder.encode(signUpReq.getLoginPw());
        signUpReq.changePw(encodedPw);
        User signupUser = User.of(signUpReq);
        User savedUser = userRepository.save(signupUser);
        return SignupRes.of(savedUser);
    }

    @Transactional(readOnly = true)
    public void login(LoginReq loginReq, HttpServletResponse response) {
        Optional<User> findUser = userRepository.findUserByLoginId(loginReq.getLoginId());
        User loginUser = findUser.orElseThrow(NotExistUserException::new);
        if(! passwordEncoder.matches(loginReq.getLoginPw(), loginUser.getLoginPw())) {
            throw new WrongPwException();
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginUser.getLoginId(), loginUser.getRole()));
    }
}
