package io.dcns.wantitauction.domain.user.service;


import static io.dcns.wantitauction.domain.user.UserCommonTest.TEST_USER;
import static io.dcns.wantitauction.domain.user.UserCommonTest.TEST_USER_LOGIN_REQUEST_DTO;
import static io.dcns.wantitauction.domain.user.UserCommonTest.TEST_USER_SIGNUP_REQUEST_DTO;
import static io.dcns.wantitauction.domain.user.UserCommonTest.TOKEN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.dcns.wantitauction.domain.user.entity.UserRoleEnum;
import io.dcns.wantitauction.domain.user.repository.UserRepository;
import io.dcns.wantitauction.global.jwt.JwtUtil;
import jakarta.persistence.EntityExistsException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @DisplayName("회원 가입")
    @Test
    void signup() {

        // given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByNickname(anyString())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

        // when, then
        assertDoesNotThrow(
            () -> userService.signup(TEST_USER_SIGNUP_REQUEST_DTO)
        );
        verify(userRepository, times(1)).save(any());
    }

    @DisplayName("회원 가입 실패 - 중복된 사용자")
    @Test
    void signup_fail_duplicateUser() {

        // given
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when, then
        assertThrows(EntityExistsException.class,
            () -> userService.signup(TEST_USER_SIGNUP_REQUEST_DTO)
        );
    }

    @DisplayName("회원 가입 실패 - 중복된 Nickname")
    @Test
    void signup_fail_duplicateNickname() {

        // given
        given(userRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThrows(EntityExistsException.class,
            () -> userService.signup(TEST_USER_SIGNUP_REQUEST_DTO)
        );
    }

    @DisplayName("로그인")
    @Test
    void login() {
        // given
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(TEST_USER));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        given(jwtUtil.generateAccessAndRefreshToken(nullable(Long.class), eq(UserRoleEnum.USER)))
            .willReturn(TOKEN);

        // when
        String token = userService.login(TEST_USER_LOGIN_REQUEST_DTO);

        // then
        assertNotNull(token);
    }

    @Test
    void logout() {
    }

    @DisplayName("프로필 수정")
    @Test
    void updateUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void deleteUser() {
    }
}
