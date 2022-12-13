package sparta.spartaproject.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Getter
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
        http.authorizeRequests()
                .antMatchers("/**", "/api/**", "/api/user/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        return http.build();
    }
}