package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathRepository extends JpaRepository<Path, Long> {
    Path findPathByPathId(String pathId);
}
