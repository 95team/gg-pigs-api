## Security

SpringSecurity 클래스들을 GG-PIGS 에 맞게 재정의한 모듈

<br>

### GPUserDetailsService (Interface)

- (Spring Security)UserDetailsService 상속한 인터페이스
  - 구현체 : GPSessionUserDetailsService, GPJwtUserDetailsService, ...

**주요 메소드**

- `GPUserDetails loadUserByHttpServletRequest(HttpServletRequest request)` : Get UserDetails by HttpServletRequest
- `GPAuthenticationToken loadAuthenticationByUser(GPUserDetails gpUserDetails)` : Get Authentication by UserDetails
- `default GPAuthenticationToken loadEmptyAuthentication()` : Empty Authentication (authenticated : true)

```java
public interface GPUserDetailsService extends UserDetailsService {

    GPUserDetails loadUserByUsername(String username) throws GPSecurityException;

    GPUserDetails loadUserByHttpServletRequest(HttpServletRequest request) throws GPSecurityException;

    GPAuthenticationToken loadAuthenticationByUser(GPUserDetails gpUserDetails) throws GPSecurityException;

    /**
     * [NOTE]
     * <br><br>
     * default loadEmptyAuthentication()는 authenticatedEmptyToken 토큰을 사용합니다.
     * <br><br>
     *
     *   - Security Filter 는 통과됨을 의미합니다.<br>
     *   - authority, role 레벨 등으로 필터링(요청에 대한 allow/disallow)합니다.<br>
     *
     * <br><br>
     * authenticatedEmptyToken 을 사용하지 않을 경우, loadEmptyAuthentication 오버라이딩하여 사용합니다.
     * <br><br>
     *
     *   - e.g. unauthenticatedEmptyToken 토큰을 사용할 수 있습니다.<br>
     *   - (anonymous 요청에 대해)WebSecurity.ignoring() 를 통해 allow 할 수 있습니다.
     * */
    default GPAuthenticationToken loadEmptyAuthentication() {
        return GPAuthenticationToken.authenticatedEmptyToken();
    }
}

```

<br>

###  GPAccessDeniedHandler, GPAuthenticationEntryPoint (Interface)

- (Spring Security)AccessDeniedHandler, AuthenticationEntryPoint 상속한 인터페이스
- 각 인터페이스의 (default 메서드) `handle()`, `commence()` 재정의

<br>

### GPAuthenticationFilter (Filter Class)

- OncePerRequestFilter 상속한 Filter ((Spring Security)AuthenticationFilter 상속 클래스 X)
- AuthenticationFilter + AuthenticationProvider 기능 포함
- **로그인 과정은 각 Application 혹은 별도의 로그인 service 에서 처리한다는 전제하에 동작<br>**
  1. 인증 : 로그인 데이터(세션, jwt) 등을 확인/검증<br>
  1-1. 인증 성공 시, SecurityContext authentication 저장<br>
  1-2. 인증 실패 시, Empty Authentication(loadEmptyAuthentication) 반환<br>
     - loadEmptyAuthentication 는 재정의하여 사용 가능

<br>

### GPAuthenticationToken (Class)

- (SpringSecurity) AbstractAuthenticationToken 상속한 클래스
- (객체 반환)static 메서드 정의
  - `GPAuthenticationToken authenticatedEmptyToken()`
  - `GPAuthenticationToken unauthenticatedEmptyToken()`
- UsernamePasswordAuthenticationToken 사용 X
  - `credentials` 속성 불필요

<br>