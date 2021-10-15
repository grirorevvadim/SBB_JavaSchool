package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootRepository extends JpaRepository<Root,Long> {
    Root findRootByRootId(String rootId);
}
