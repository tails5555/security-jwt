package io.kang.securityjwt.component;

import io.kang.securityjwt.domain.Role;
import io.kang.securityjwt.domain.RoleUnit;
import io.kang.securityjwt.domain.User;
import io.kang.securityjwt.repository.RoleRepository;
import io.kang.securityjwt.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class BootInitRunner implements ApplicationRunner {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public BootInitRunner(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Role> userRoleOpt = roleRepository.findByType(RoleUnit.ROLE_USER);
        Optional<Role> adminRoleOpt = roleRepository.findByType(RoleUnit.ROLE_ADMIN);

        Role userRole, adminRole;

        if (userRoleOpt.isPresent()) {
            userRole = userRoleOpt.get();
        } else {
            userRole = new Role(0L, RoleUnit.ROLE_USER);
            userRole = roleRepository.save(userRole);
        }

        if (adminRoleOpt.isPresent()) {
            adminRole = adminRoleOpt.get();
        } else {
            adminRole = new Role(0L, RoleUnit.ROLE_ADMIN);
            adminRole = roleRepository.save(adminRole);
        }

        if (!userRepository.existsById("tester_user")) {
            userRepository.save(
                User.builder()
                        .userId("tester_user")
                        .password(passwordEncoder.encode("tester_user"))
                        .name("테스터_사용자")
                        .email("tester_user@test.com")
                        .roles(Set.of(userRole))
                        .build()
            );
        }

        if (!userRepository.existsById("tester_admin")) {
            userRepository.save(
                    User.builder()
                            .userId("tester_admin")
                            .password(passwordEncoder.encode("tester_admin"))
                            .name("테스터_어드민")
                            .email("tester_admin@test.com")
                            .roles(Set.of(adminRole))
                            .build()
            );
        }

        if (!userRepository.existsById("tester_all")) {
            userRepository.save(
                    User.builder()
                            .userId("tester_all")
                            .password(passwordEncoder.encode("tester_all"))
                            .name("테스터_모든권한")
                            .email("tester_all@test.com")
                            .roles(Set.of(adminRole, userRole))
                            .build()
            );
        }
    }
}
