package com.project.oneshot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //시큐리티 설정파일을 시큐리티 필터에 등록
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf토큰 사용x
        http.csrf().disable();
        http.authorizeRequests(authorize -> authorize
                .antMatchers("/","/common/login","/common/loginForm","/common/**").permitAll() //로그인페이지는 로그인 안해도 접근 가능하게함
                .antMatchers("/user/**").hasRole("USER") // /user/** 경로에 USER 권한 요구
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // USER or ADMIN중 한개만 있으면 됨
                .antMatchers(HttpMethod.POST, "/user/**").hasRole("USER") // /user/** 경로에 대한 POST 요청은 USER 권한 요구
                .antMatchers(HttpMethod.POST, "/secure/**").access("hasRole('USER') and hasRole('ADMIN')") // USER와 ADMIN 권한 모두 필요
                .anyRequest().authenticated() // 이외의 모든 요청에 대해 인증 요구(꼭 마지막에)

        );

        http
                .formLogin()
                .loginPage("/common/login") //사용자가 제공하는 폼기반 로그인 기능을 사용할 수 있습니다.
                .loginProcessingUrl("/common/loginForm") //로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결합니다.
                .failureUrl("/common/login?err=true") //로그인 실패시 이동페이지
		        .defaultSuccessUrl("/common/loginTest"); //로그인후에 기본적으로 이동할 페이지

        http.logout()
                .logoutUrl("/logout") // 로그아웃을 위한 URL
                .logoutSuccessUrl("/common/login") // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID"); // 쿠키 삭제

        return http.build();
    }

}