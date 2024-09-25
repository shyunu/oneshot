package com.project.oneshot.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // 로그인 실패 시 알림 메시지 설정
        String errorMessage = "아이디 및 비밀번호를 확인하세요"; // 기본 메시지
        // 예외의 타입에 따라 메시지를 다르게 설정할 수도 있음
        // if (exception instanceof SomeSpecificException) {
        //     errorMessage = "특정 오류 메시지";
        // }

        response.setContentType("text/plain; charset=utf-8"); // 응답 타입 설정
        response.getWriter().write(errorMessage); // 메시지를 응답으로 전송
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
    }
}
