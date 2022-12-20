package io.kang.securityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping(UserRestController.API_URI_PREFIX)
public class UserRestController {
    // 아래 URL 은 아무거나 해도 상관 없다.
    public static final String API_URI_PREFIX = "/user";

    // USER 권한을 가져야 아래 데이터들을 접근할 수 있다.
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
