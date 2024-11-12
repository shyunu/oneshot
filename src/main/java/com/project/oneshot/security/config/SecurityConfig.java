package com.project.oneshot.security.config;

import com.project.oneshot.security.handler.CustomAccessDeniedHandler;
import com.project.oneshot.security.handler.CustomAuthenticationFailureHandler;
import com.project.oneshot.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //시큐리티 설정파일을 시큐리티 필터에 등록
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;



    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf토큰 사용x
        http.csrf().disable();

        http.authorizeRequests(authorize -> authorize
                .antMatchers("/","/common/login","/common/loginForm","/common/js/**","/common/css/**","/common/img/**").permitAll() //로그인페이지는 로그인 안해도 접근 가능하게함
                .antMatchers("/contractApp/**","/inventoryApp/**","/salesApp/**","/productApp/**","/homeApp/**").permitAll()
                .antMatchers("/hrm/attendance/**", "/hrm/attendance.do").permitAll() // 근태관리 페이지는 모든 사용자 접근 가능
                .antMatchers("/hrm/**").hasRole("1")
                .antMatchers("/inventory/**").hasRole("2")
                .antMatchers("/sales/**").hasRole("3")
                .anyRequest().authenticated() // 이외의 모든 요청에 대해 인증 요구(꼭 마지막에)
        );
        http
                .formLogin()
                .loginPage("/common/login") //사용자가 제공하는 폼기반 로그인 기능을 사용할 수 있습니다.
                .loginProcessingUrl("/common/loginForm") //로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결합니다.
                .successHandler(successHandler) // 성공 시 핸들러 설정
                .failureHandler(failureHandler); // 실패 시 핸들러 설정

        http.logout()
                .logoutUrl("/logout") // 로그아웃을 위한 URL
                .logoutSuccessUrl("/common/login") // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID"); // 쿠키 삭제

        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        return http.build();
    }
}