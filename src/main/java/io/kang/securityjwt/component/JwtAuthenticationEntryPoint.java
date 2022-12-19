package io.kang.securityjwt.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized Error Checking -> {}", authException.getMessage());
        // ErrorCode 에 대해서 받아올 방법을 다시 확인해볼 것.
        //ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute("unauthorization.code");
        //request.setAttribute("response.failure.code", unAuthorizationCode.name());
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode.message());
    }
}
