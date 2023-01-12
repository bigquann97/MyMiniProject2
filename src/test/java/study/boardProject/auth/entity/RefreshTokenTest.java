package study.boardProject.auth.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.boardProject.factory.RefreshTokenFactory;

import static org.assertj.core.api.Assertions.*;

class RefreshTokenTest {

        @DisplayName("1. 리프레시 토큰 빌더 테스트")
        @Test
        void test_1() {
        //given
        RefreshToken build = RefreshToken.builder()
                .refreshToken("refresh")
                .email("email")
                .build();

        //when, then
        assertThat(build.getRefreshToken()).isEqualTo("refresh");
        assertThat(build.getEmail()).isEqualTo("email");
        }

        @DisplayName("2. 리프레시 토큰 갱신 테스트")
        @Test
        void test_2() {
        //given
        RefreshToken token = RefreshTokenFactory.buildRefreshToken();
        String updateElement = "update";

        //when
        token.updateValue(updateElement);

        //then
        assertThat(token.getRefreshToken()).isEqualTo(updateElement);
        }

        @DisplayName("3. 소유자 유효성 검증 테스트")
        @Test
        void test_3 () {
        //given
        RefreshToken token = RefreshTokenFactory.buildRefreshToken();
        RefreshToken sameToken = RefreshTokenFactory.buildRefreshToken();
        RefreshToken anotherToken = RefreshTokenFactory.buildRefreshToken();

        //when
        anotherToken.updateValue("anotherValue");

        //then
        assertThat(token.validateOwner(sameToken.getRefreshToken())).isTrue();
        assertThat(token.validateOwner(anotherToken.getRefreshToken())).isFalse();
        }

}