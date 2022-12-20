package io.kang.securityjwt.service;

import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.exception.UserDomainException;
import io.kang.securityjwt.repository.UserRepository;
import io.kang.securityjwt.vo.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(String id) throws UserDomainException {
        return userRepository
                    .findById(id)
                    .orElseThrow(
                        () -> new UserDomainException(String.format("아이디 [%s] 을 가진 회원은 없습니다.", id))
                    );
    }

    @Override
    public User login(Token.Request request) throws UserDomainException {
        User user = findById(request.getId());
        if (user != null && !passwordEncoder.matches(request.getPasswd(), user.getPassword())) {
            throw new UserDomainException(String.format("아이디 [%s] 회원의 비밀번호가 일치하지 않습니다.", request.getId()));
        }
        return user;
    }
}
