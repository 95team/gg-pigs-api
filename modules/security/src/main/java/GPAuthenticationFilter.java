import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GPAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log;
    private final GPUserDetailsService gpUserDetailsService;

    public GPAuthenticationFilter(GPUserDetailsService gpUserDetailsService) {
        this.gpUserDetailsService = gpUserDetailsService;
        this.log = LoggerFactory.getLogger(GPAuthenticationFilter.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        GPUserDetails gpUserDetails = null;
        GPAuthenticationToken gpAuthenticationToken = null;

        try {
            gpUserDetails = gpUserDetailsService.loadUserByHttpServletRequest(request);
            gpAuthenticationToken = gpUserDetailsService.loadAuthenticationByUser(gpUserDetails);
        } catch (Exception e) {
            log.info(e.getMessage());

            gpAuthenticationToken = gpUserDetailsService.loadEmptyAuthenticationByUser();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(gpAuthenticationToken);

            filterChain.doFilter(request, response);
        }
    }
}
