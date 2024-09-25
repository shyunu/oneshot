package com.project.oneshot.security.handler;

import com.project.oneshot.security.service.EmployeeDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();

        // 퇴직 상태 확인
        if ("n".equals(employeeDetails.getEmployeeStatus())) {
            String errorMessage = "퇴사 처리된 직원입니다"; // 기본 메시지
            // 예외의 타입에 따라 메시지를 다르게 설정할 수도 있음
            // if (exception instanceof SomeSpecificException) {
            //     errorMessage = "특정 오류 메시지";
            // }

            response.setContentType("text/plain; charset=utf-8"); // 응답 타입 설정
            response.getWriter().write(errorMessage); // 메시지를 응답으로 전송
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        } else {
            response.sendRedirect("/common/home");
        }
    }
}
