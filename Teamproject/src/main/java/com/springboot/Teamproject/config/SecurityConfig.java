package com.springboot.Teamproject.config;

import com.springboot.Teamproject.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        //어떤 파일이던 접근 가능하도록 허용
        http.authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")       //로그인 시 매핑
                .defaultSuccessUrl("/")         //성공 시 매핑
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))    //로그아웃 요청 및 매핑
                .logoutSuccessUrl("/")      //로그아웃 성공 시 매핑
                .invalidateHttpSession(true)    //로그인 세션 끊음
        ;

        return http.build();
    }

    //비밀번호 암호화 하는 기능
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
