package io.kang.securityjwt.service;

import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.exception.UserDomainException;

public interface UserService {
    User findById(String id) throws UserDomainException;
}
