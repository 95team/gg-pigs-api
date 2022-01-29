package com.gg_pigs.modules.security;

import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface GPUserDetailsService extends UserDetailsService {

    GPUserDetails loadUserByUsername(String username) throws GPSecurityException;

    GPUserDetails loadUserByHttpServletRequest(HttpServletRequest request) throws GPSecurityException;

    GPAuthenticationToken loadAuthenticationByUser(GPUserDetails gpUserDetails) throws GPSecurityException;

    /**
     * [NOTE]
     * <br><br>
     * default loadEmptyAuthenticationByUser()는 authenticatedEmptyToken 토큰을 사용합니다.
     * <br><br>
     *
     *   - Security Filter 는 통과됨을 의미합니다.<br>
     *   - authority, role 레벨 등으로 필터링(요청에 대한 allow/disallow)합니다.<br>
     *
     * <br><br>
     * authenticatedEmptyToken 을 사용하지 않을 경우, loadEmptyAuthenticationByUser 오버라이딩하여 사용합니다.
     * <br><br>
     *
     *   - e.g. unauthenticatedEmptyToken 토큰을 사용할 수 있습니다.<br>
     *   - (anonymous 요청에 대해)WebSecurity.ignoring() 를 통해 allow 할 수 있습니다.
     * */
    default GPAuthenticationToken loadEmptyAuthenticationByUser() {
        return GPAuthenticationToken.authenticatedEmptyToken();
    }
}
