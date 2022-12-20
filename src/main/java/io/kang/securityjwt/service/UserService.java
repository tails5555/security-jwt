package io.kang.securityjwt.service;

import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.exception.UserDomainException;
import io.kang.securityjwt.vo.Token;

public interface UserService {
    User findById(String id) throws UserDomainException;
    User login(Token.Request request) throws UserDomainException;
}
