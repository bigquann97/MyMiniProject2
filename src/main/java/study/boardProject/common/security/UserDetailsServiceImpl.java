package study.boardProject.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.auth.repository.UserRepository;

import static study.boardProject.common.exception.AuthException.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserDetailsImpl createUserDetails(User user) {
        return new UserDetailsImpl(user, user.getEmail());
    }

}
