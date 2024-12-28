package com.tongji.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .cors().disable()
                .csrf().disable()  // 禁用 CSRF 防护，适用于非浏览器客户端的应用
                .authorizeRequests()
                .requestMatchers("/user/login", "/user/register","/admin/login","/coach/login").permitAll()  // 允许登录和注册请求不需要认证
                .anyRequest().authenticated()  // 其他请求需要认证
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // 在用户名密码认证过滤器之前加入自定义 JWT 过滤器

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 允许的来源
        configuration.addAllowedMethod("*"); // 允许的 HTTP 方法
        configuration.addAllowedHeader("*"); // 允许的请求头
        configuration.setAllowCredentials(true); // 是否允许凭据
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 配置应用到所有路径
        return source;
    }
}