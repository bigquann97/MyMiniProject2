package sparta.spartaproject.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sparta.spartaproject.controller.UserController;
import sparta.spartaproject.dto.token.TokenResponse;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Spy
    ResultService resultService;

    MockMvc mockMvc;
    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("1. 회원가입 테스트")
    @Test
    void test_1() throws Exception {
        SignupRequest req = SignupRequest.builder()
                .age(26)
                .name("김관호")
                .loginId("temp1234")
                .loginPw("temp1234")
                .loginPwAgain("temp1234")
                .adminKey("makeMeAdmin")
                .wantAdmin(false)
                .email("temp1234@naver.com")
                .build();

        mockMvc.perform(
                        post("/api/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(userService).signup(refEq(req));
    }

    @DisplayName("2. 로그인 테스트")
    @Test
    void test_2() throws Exception {
        LoginRequest req = new LoginRequest("temp1234", "temp1234");
        when(userService.login(any(req.getClass()))).thenReturn(TokenResponse.builder().accessToken("at").refreshToken("rt").build());

        mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").value("rt"));
    }

}