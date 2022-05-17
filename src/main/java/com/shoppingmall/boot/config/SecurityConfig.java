package com.shoppingmall.boot.config;

import com.shoppingmall.auth.exceptions.AuthenticationEntry;
import com.shoppingmall.auth.filter.AuthJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntry authenticationEntry;
    private final AuthJwtFilter authJwtFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // CORS 허용
        http.cors().configurationSource(corsConfigurationSource()).and();

        // CSRF 해제
        http.csrf().disable();

        // JWT 사용을 위한 세션 Stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // 필터 등록 및 Entry 처리
        http.authorizeRequests()
                .antMatchers("/auth/sign-out").authenticated()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/user/with-auth/**").hasRole("USER")
                        .and();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntry).and();
        http.addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
