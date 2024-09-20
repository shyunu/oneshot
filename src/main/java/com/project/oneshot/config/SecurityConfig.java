package com.project.oneshot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        // /logout으로 로그아웃을 시도하고 URL주소를 확인하세요
        //모든 요청에 대해 사용자 인증이 필요합니다.
//		http.authorizeRequests( (authorize) -> authorize.anyRequest().authenticated() );

        //user페이지에 대해서만 사용자 인증이 필요합니다.
//		http.authorizeRequests( (authorize) -> authorize.antMatchers("/user/**").authenticated() );

        //user페이지에 대해서 user권한이 필요합니다.
//		http.authorizeRequests( (authorize) -> authorize.antMatchers("/user/**").hasRole("USER")  );

        //user페이지에 대해서 user권한이 필요합니다. admin페이지에 대해서 admin권한이 필요합니다.
//		http.authorizeRequests( (authorize) -> authorize.antMatchers("/user/**").hasRole("USER")
//														.antMatchers("/admin/**").hasRole("ADMIN") );

        //all페지이는 인증만되면 들어갑니다. user페이지에 대해서 user권한이 필요합니다. admin페이지에 대해서 admin권한이 필요합니다.
        //나머지 모든 요청은 요청을 허용합니다.
//		http.authorizeRequests( (authorize) -> authorize.antMatchers("/all").authenticated()
//														.antMatchers("/user/**").hasRole("USER")
//														.antMatchers("/admin/**").hasRole("ADMIN")
//														.anyRequest().permitAll() );

        //all페지이는 인증만되면 들어갑니다.
        //user페이지에 대해서 user권한 or ADMIN 이필요합니다.
        //admin페이지에 대해서 admin권한이 필요합니다.
        //나머지 모든 요청은 요청을 허용합니다.
        http.authorizeRequests( (authorize) -> authorize
                .antMatchers("/all").authenticated()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll() );

        //시큐리티가 제공하는 폼기반 로그인 기능을 사용할 수 있습니다.
        //이후 권한이 없으면 시큐리티는 낚아채서 기본 로그인을 보여줍니다.
        //	http.formLogin( Customizer.withDefaults() );

        //사용자가 제공하는 폼기반 로그인 기능을 사용할 수 있습니다.
        http
                .formLogin()
                .loginPage("/login") //사용자가 제공하는 폼기반 로그인 기능을 사용할 수 있습니다.
                .loginProcessingUrl("/loginForm") //로그인 페이지를 가로채 시큐리티가 제공하는 클래스로 로그인을 연결합니다.
		        .defaultSuccessUrl("/hello"); //

        return http.build();
    }

}