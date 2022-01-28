package com.gg_pigs.global.config;

import com.gg_pigs.app.user.entity.UserRole;
import com.gg_pigs.global.security.GPAccessDeniedHandler;
import com.gg_pigs.global.security.GPAuthenticationEntryPoint;
import com.gg_pigs.global.security.GPSessionUserDetailsService;
import com.gg_pigs.modules.security.GPAuthenticationFilter;
import com.gg_pigs.modules.security.GPAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * [References]
 * 1. Spring Boot Security Auto Configuration 끄기, https://thecodinglog.github.io/spring/security/2020/01/10/disable-security-auto-config.html
 * 2. [스프링 부트 개념과 활용] 스프링 시큐리티, https://dailyheumsi.tistory.com/185
 * 3. [Spring Boot] Spring Security 적용하기, https://bamdule.tistory.com/53
 * 4. Spring Security Configuration - HttpSecurity vs WebSecurity, https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity
 * 5. Spring Security CSRF 설정, https://cheese10yun.github.io/spring-csrf/
 * 6. SpringBoot2로 Rest api 만들기(8) – SpringSecurity 를 이용한 인증 및 권한부여, https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/
 * 7. [Spring Security] csrf token handling, https://hakurei.tistory.com/3
 * */

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final GPSessionUserDetailsService gpSessionDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 1. Spring Security should completely ignore URLs starting with '/'
        // 2. WebSecurity는 FilterChainProxy를 생성하는 필터입니다. 다양한 Filter 설정을 적용할 수 있습니다.
        //    - Spring Security에서 해당 요청은 인증 대상에서 제외시킵니다.

        web.ignoring()
                .antMatchers("/api/v1/login")
                .antMatchers("/static/**")
                .antMatchers("/health/**")
                .antMatchers(HttpMethod.OPTIONS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * 기타(session, filter, deniedHandler, ...) 설정입니다.
         * */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new GPAuthenticationFilter(gpSessionDetailsService), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(new GPAccessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(new GPAuthenticationEntryPoint());

        /*
         * csrf 설정입니다.
         * */
        http.csrf().ignoringAntMatchers("/api/v1/**");
        http.csrf().ignoringAntMatchers("/api/v2/**");

        /*
         * authorizeRequests 설정입니다.
         * */
        http.authorizeRequests().antMatchers(HttpMethod.POST).authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.GET).authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.PUT).hasAnyRole(UserRole.ROLE_ADMIN.suffixName(), UserRole.ROLE_USER.suffixName());
        http.authorizeRequests().antMatchers(HttpMethod.DELETE).hasRole(UserRole.ROLE_ADMIN.suffixName());
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(new GPAuthenticationProvider());
    }
}
