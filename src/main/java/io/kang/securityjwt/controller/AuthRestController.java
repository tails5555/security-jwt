package io.kang.securityjwt.controller;

import io.kang.securityjwt.component.JwtTokenProvider;
import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.service.UserService;
import io.kang.securityjwt.vo.Token;
import io.kang.securityjwt.vo.UserAuthentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(AuthRestController.URL_PREFIX)
public class AuthRestController {
    public static final String API_URI_PREFIX = "/test";
    public static final String URL_PREFIX = API_URI_PREFIX + "/auth";
    public static final String LOGIN = "/login";

    private UserService userService;

    public AuthRestController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
        value = LOGIN,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(
        final HttpServletRequest req, final HttpServletResponse res, @RequestBody Token.Request request
    ) throws Exception {
        User user = userService.login(request);
        Authentication authentication = new UserAuthentication(user.getUserId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);

        Token.Response response = new Token.Response(token);
        return ResponseEntity.ok(response);
    }
}
