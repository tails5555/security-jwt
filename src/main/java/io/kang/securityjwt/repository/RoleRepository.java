package io.kang.securityjwt.repository;

import io.kang.securityjwt.domain.Role;
import io.kang.securityjwt.domain.RoleUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByType(RoleUnit name);
}
