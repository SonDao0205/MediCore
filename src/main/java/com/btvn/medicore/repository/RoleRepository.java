package com.btvn.medicore.repository;
import com.btvn.medicore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}