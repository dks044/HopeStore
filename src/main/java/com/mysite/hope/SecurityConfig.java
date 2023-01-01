package com.mysite.hope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //@Configuration은 스프링의 환경설정 파일임을 의미하는 애너테이션
@EnableWebSecurity //@EnableWebSecurity는 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers( 
                new AntPathRequestMatcher("/**")).permitAll() //모든 인증되지 않은 요청을 허락한다는 의미
        	.and()
        		.csrf().ignoringRequestMatchers( //스프링 시큐리티가 CSRF 처리시 H2 콘솔은 예외로 처리할 수 있도록
        				new AntPathRequestMatcher("/h2-console/**"))
        	.and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
            .and()
                .formLogin() //스프링 시큐리티에 로그인 URL을 등록
                .loginPage("/user/login")//스프링 시큐리티의 로그인 설정을 담당
                .defaultSuccessUrl("/")//로그인 성공시 루트 url
            .and()
                .logout() 
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //로그아웃 URL을 /user/logout으로 설정
                .logoutSuccessUrl("/") //로그아웃이 성공하면 루트(/) 페이지로 이동
                .invalidateHttpSession(true) //로그아웃시 생성된 사용자 세션도 삭제하도록 처리
        		;
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
