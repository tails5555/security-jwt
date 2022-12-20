package io.kang.securityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(AuthRestController.API_URI_PREFIX)
public class AuthRestController2 {
    public static final String API_URI_PREFIX = "/test";

    @Secured("ROLE_ADMIN")
    @GetMapping("admin")
    public ResponseEntity<?> roleAdmin() {
        return ResponseEntity.ok(
                new HashMap<String, Object>() {{ put("message", "OK, ADMIN!"); }}
        );
    }

    @Secured("ROLE_USER")
    @GetMapping("user")
    public ResponseEntity<?> roleUser() {
        return ResponseEntity.ok(
                new HashMap<String, Object>() {{ put("message", "OK, USER!"); }}
        );
    }

    @GetMapping("hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(
                new HashMap<String, Object>() {{ put("message", "HELLO!"); }}
        );
    }
}
