package io.kang.securityjwt.vo;

import lombok.*;

public class Token {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Request {
        private String id;
        private String passwd;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response {
        private String token;
    }
}
