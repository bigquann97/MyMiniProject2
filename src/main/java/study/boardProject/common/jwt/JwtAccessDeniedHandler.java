package study.boardProject.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;
import study.boardProject.common.exception.TokenException;
import study.boardProject.common.exception.api.RestApiException;
import study.boardProject.common.exception.api.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error(Status.AUTH.getMsg());
        response.setStatus(400);
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new RestApiException(Status.AUTH_AUTHENTICATION));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
}
