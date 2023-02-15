package com.example.hospitalproject.Config.security;

import com.example.hospitalproject.Config.jwt.JwtAccessDeniedHandler;
import com.example.hospitalproject.Config.jwt.JwtAuthenticationEntryPointHandler;
import com.example.hospitalproject.Config.jwt.JwtSecurityConfig;
import com.example.hospitalproject.Config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfigure {

    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPointHandler jwtAuthenticationEntryPointHandler;

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPointHandler)

                .and()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers("/home/user/sign-in", "/home/user/sign-up", "/home/user/id", "/home/user/password/reissue", "/home/user/sign-up/confirm/email/valid", "/confirm/email").permitAll()
                .antMatchers("/chat/**").access("hasRole(\"ADMIN\") or hasRole(\"MANAGER\") or hasRole(\"EXCELLENT_MEMBER\") or hasRole(\"COMMON_MEMBER\")")
                .antMatchers("/chatting/**").access("hasRole(\"ADMIN\") or hasRole(\"MANAGER\") or hasRole(\"EXCELLENT_MEMBER\") or hasRole(\"COMMON_MEMBER\")")
                .antMatchers("/payment/common/**").access("hasRole(\"ADMIN\") or hasRole(\"MANAGER\") or hasRole(\"EXCELLENT_MEMBER\") or hasRole(\"COMMON_MEMBER\")")
                .antMatchers("/payment/card/**").access("hasRole(\"ADMIN\") or hasRole(\"MANAGER\") or hasRole(\"EXCELLENT_MEMBER\") or hasRole(\"COMMON_MEMBER\")")
                .antMatchers("/payment/manager/**").access("hasRole(\"ADMIN\") or hasRole(\"MANAGER\")")
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}
