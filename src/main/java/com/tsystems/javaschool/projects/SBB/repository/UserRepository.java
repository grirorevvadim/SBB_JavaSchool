package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
