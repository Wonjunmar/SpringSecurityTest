package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login","/member/signup").permitAll()      // 앞으로 요청은 인증한 것만 받아라 .antMatchers().permitAll(), 모두 허용 .permitAll()
                    .antMatchers("/member/mypage").hasRole("USER")
                    .anyRequest().authenticated();        // 어떤 요청이든 거부해라 .anyRequest().denyAll(); ,          모두 거부 .denyAll()

            http.formLogin()
                    .loginProcessingUrl("/member/login")
                    .defaultSuccessUrl("/member/mypage");   // 로그인 성공 시 가는 페이지
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
