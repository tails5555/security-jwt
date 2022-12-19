package io.kang.securityjwt.service;

import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.exception.UserDomainException;
import io.kang.securityjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String id) throws UserDomainException {
        return userRepository
                    .findById(id)
                    .orElseThrow(
                        () -> new UserDomainException(String.format("아이디 [%s] 을 가진 회원은 없습니다.", id))
                    );
    }
}
