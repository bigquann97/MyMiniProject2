package study.boardProject.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;
import study.boardProject.auth.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthService authService;

    MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @DisplayName("1. 정상 회원가입 테스트")
    @Test
    void test_1() throws Exception {

        //given
        SignupRequest req = new SignupRequest("tempid", "tempPw", "닉네임", "이름", "email@naver.com", 30);

        //when, then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/auth/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req)))
                .andExpect(
                        MockMvcResultMatchers
                                .status().isCreated());

        Mockito.verify(authService).signup(req);
    }

    @DisplayName("2. 정상 로그인 테스트")
    @Test
    void test_2() throws Exception {
        // given
        LoginRequest req = new LoginRequest("tempid", "temppw");

        when(authService.login(eq(req), any()))
                .thenReturn(new TokenResponse("access", "refresh"));

        // when, then
        mockMvc.perform(
                        post("/api/auth/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req)))
                .andExpect(
                        status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.accessToken")
                                .value("access"))
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.refreshToken")
                                .value("refresh")
                );

        verify(authService).login(eq(req), any());
    }

    @DisplayName("3. 정상 Re-Issue 테스트")
    @Test
    void test_3() throws Exception {
        TokenRequest req = new TokenRequest("access", "refresh");

        when(authService.reissue(req, any()))
                .thenReturn(new TokenResponse("access", "refresh"));

        mockMvc.perform(
                        post("/api/auth/re-issue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(req))
                ).andExpect(MockMvcResultMatchers
                        .jsonPath("$.accessToken")
                        .value("access"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.refreshToken")
                        .value("refresh"));

        verify(authService).reissue(eq(req), any());
    }
}