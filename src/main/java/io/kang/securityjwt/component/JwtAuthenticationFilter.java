package io.kang.securityjwt.component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (!StringUtils.isEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {
                String userId = JwtTokenProvider.getUserIdFromJwt(jwt);

                // 비밀번호 내용도 같이 넣어줘야 하는게 도리 아닐까?
                UserAuthentication authentication = new UserAuthentication(userId, null, null);
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
        String bearerToken = request.getHeader("Authentication");
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(JWT_TOKEN_PREFIX.length());
        }
        return null;
    }

    public static class UserAuthentication extends UsernamePasswordAuthenticationToken {
        public UserAuthentication(String principal, String credentials) {
            super(principal, credentials);
        }

        public UserAuthentication(String principal, String credentials,
                                  List<GrantedAuthority> authorities) {
            super(principal, credentials, authorities);
        }
    }
}
