package space.adorer.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import space.adorer.admin.filter.JwtAuthenticationFilter;

/**
 * Spring Security 配置类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:34:48
 */
@Configuration
public class SecurityConfig {

    /**
     * 白名单
     */
    private final String[] ALLOW_LIST = {
            "/report",              // 上传访客记录
            "/auth/login",          // 登录请求
            "/auth/captcha.jpg"     // 获取验证码请求
    };

    /**
     * 配置密码加密器
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取 AuthenticationManager , 用于自定义登录接口中用户的认证和登录
     */
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * 配置 SecurityFilterChain
     * 配置 HttpSecurity 放行请求后任然会走过滤器链，只是不想要鉴权，若想不走过滤器连，可配置 WebSecurity
     */
    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter) throws Exception {
        return http
                // 关闭 csrf
                .csrf().disable()
                // 不使用 session
                .sessionManagement().disable()
                // 配置 http 请求认证规则
                .authorizeHttpRequests(authorize -> authorize
                        // 预检请求全部放行
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        // 白名单中的请求全部放行
                        .requestMatchers(ALLOW_LIST).permitAll()
                        // 其他所有请求都需要鉴权
                        .anyRequest().authenticated())
                // 在 UsernamePasswordAuthenticationFilter 前加上 JwtAuthenticationFilter
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                // 构建 SecurityFilterChain
                .build();
    }
}