package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
