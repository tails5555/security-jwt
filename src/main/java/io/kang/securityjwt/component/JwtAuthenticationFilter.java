package io.kang.securityjwt.component;

import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.service.UserService;
import io.kang.securityjwt.vo.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (!StringUtils.isEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {
                String userId = JwtTokenProvider.getUserIdFromJwt(jwt);
                User user = userService.findById(userId);
                UserAuthentication authentication = new UserAuthentication(userId, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if (StringUtils.isEmpty(jwt)) {
                    request.setAttribute("unauthorization", "401 인증키 미존재. 확인 바랍니다.");
                }

                if (JwtTokenProvider.validateToken(jwt)) {
                    request.setAttribute("unauthorization", "401 인증키 만료. 로그인 재시도 필요.");
                }
            }
        } catch (Exception e) {
            logger.error("Could Not Set User Authentication In Security Context");
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(JWT_TOKEN_PREFIX.length());
        }
        return null;
    }
}
