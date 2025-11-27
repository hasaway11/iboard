package com.example.iboard;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.web.cors.*;

import java.util.*;

@EnableMethodSecurity(securedEnabled=true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  // 로그인이 필요한 경우 처리 : 기본값은 로그인 페이지로 이동이지만 REST 방식이므로 401을 응답 -> 프론트가 알아서 처리
  private final AuthenticationEntryPoint authenticationEntryPoint;
  // 권한이 필요한 경우 처리 : 기본값은 에러 페이지를 보여주는 거지만 REST 방식이므로 403을 응답 -> 프론트가 알아서 처리
  private final AccessDeniedHandler accessDeniedHandler;
  // 로그인 성공에 대한 처리 - 200으로 응답
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  // 로그인 실패에 대한 처리 - 409로 응답
  private final AuthenticationFailureHandler authenticationFailureHandler;
  // 로그아웃 성공에 대한 처리 - 200으로 응답
  private final LogoutSuccessHandler logoutSuccessHandler;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity config) throws Exception {
    // 아래 52라인에서 생성한 CORS 설정을 등록
    config.cors(cors->cors.configurationSource(corsConfigurationSource()));
    config.csrf(csrf-> csrf.disable());

    config.formLogin(form->form.loginPage("/login").loginProcessingUrl("/login")
        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler));
    config.logout(logout-> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
    config.exceptionHandling(handler->
        handler.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));
    return config.build();
  }

  // ajax 요청에 대한 CORS 설정
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", config);
    return src;
  }
}
