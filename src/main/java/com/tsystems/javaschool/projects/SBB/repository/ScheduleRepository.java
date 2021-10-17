package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByScheduleId(String scheduleId);
}
