package com.gg_pigs._common;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.entity.UserRole;
import com.gg_pigs.global.security.GPSessionUserDetailsService;
import com.gg_pigs.global.security.GPUserDetails;
import com.gg_pigs.security.GPAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
public class SecuritySetUp4ControllerTest {

    @MockBean
    protected GPSessionUserDetailsService gpSessionUserDetailsService;

    protected final User normalUser = UserGenerator.getInstance(null, UserRole.ROLE_USER);
    protected final User adminUser = UserGenerator.getInstance(null, UserRole.ROLE_ADMIN);

    protected GPUserDetails gpUserDetails = GPUserDetailsGenerator.getInstance();
    protected GPAuthenticationToken gpAuthenticationToken = GPAuthenticationTokenGenerator.getInstance();

    /**
     * [NOTE]<br>
     *   - gpAuthenticationToken : authenticatedEmptyToken 반환합니다.<br>
     *   - 추가적인 권한 설정(ROLE 설정 등)이 필요할 경우 각 하위 클래스에서 재정의하거나 해당 클래스의 메서드를(setUpAdminRole, ...)를 호출합니다.<br>
     *     -
     * */
    @BeforeEach
    void securitySetUp() {
        Mockito.when(gpSessionUserDetailsService.loadUserByUsername(any())).thenReturn(gpUserDetails);
        Mockito.when(gpSessionUserDetailsService.loadUserByHttpServletRequest(any())).thenReturn(gpUserDetails);
        Mockito.when(gpSessionUserDetailsService.loadAuthenticationByUser(any())).thenReturn(gpAuthenticationToken);
    }

    protected void setUpUserRole() {
        gpUserDetails = GPUserDetailsGenerator.getInstance(normalUser);
        gpAuthenticationToken = GPAuthenticationTokenGenerator.getInstance(gpUserDetails);

        Mockito.when(gpSessionUserDetailsService.loadAuthenticationByUser(any())).thenReturn(gpAuthenticationToken);
    }

    protected void setUpAdminRole() {
        gpUserDetails = GPUserDetailsGenerator.getInstance(adminUser);
        gpAuthenticationToken = GPAuthenticationTokenGenerator.getInstance(gpUserDetails);

        Mockito.when(gpSessionUserDetailsService.loadAuthenticationByUser(any())).thenReturn(gpAuthenticationToken);
    }
}
