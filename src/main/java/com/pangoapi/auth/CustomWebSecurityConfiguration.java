package com.pangoapi.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * [References]
 * 1. Spring Boot Security Auto Configuration 끄기, https://thecodinglog.github.io/spring/security/2020/01/10/disable-security-auto-config.html
 * 2. [스프링 부트 개념과 활용] 스프링 시큐리티, https://dailyheumsi.tistory.com/185
 * 3. [Spring Boot] Spring Security 적용하기, https://bamdule.tistory.com/53
 * 4. Spring Security Configuration - HttpSecurity vs WebSecurity, https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity
 *
 * */

@EnableWebSecurity
@Configuration
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 1. Spring Security should completely ignore URLs starting with '/'
        // 2. WebSecurity는 FilterChainProxy를 생성하는 필터입니다. 다양한 Filter 설정을 적용할 수 있습니다.
        //    Spring Security에서 해당 요청은 인증 대상에서 제외시킵니다.

        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1. Possibly more configuration
        //    Enable form based log in
        // 2. HttpSecurity를 통해 HTTP 요청에 대한 보안을 설정할 수 있습니다.

        http.authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/**").permitAll();

        http.formLogin()
                .defaultSuccessUrl("/")
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .accessDeniedPage("/login");
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1. Enable in memory based authentication with a user named "user" and "admin"
        // 2. AuthenticationManager를 생성합니다. AuthenticationManager는 사용자 인증을 담당합니다.
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }
     */
}
