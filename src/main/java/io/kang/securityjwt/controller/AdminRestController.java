package io.kang.securityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(AdminRestController.API_URI_PREFIX)
public class AdminRestController {
    public static final String API_URI_PREFIX = "/admin";

    // ADMIN 권한을 들고 있어야 아래 데이터에 접근할 수 있다.
    @GetMapping("test")
    public ResponseEntity<?> testdata() {
        return ResponseEntity.ok(
                new HashMap<String, Object>() {{
                    put("data", 123);
                    put("text", "text");
                    put("checked", true);
                }}
        );
    }
}
