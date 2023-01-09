package study.boardProject.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import study.boardProject.common.exception.TokenException;
import study.boardProject.common.exception.api.RestApiException;
import study.boardProject.common.exception.api.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error(Status.TOKEN.getMsg());
        response.setStatus(400);
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new RestApiException(Status.AUTH));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
