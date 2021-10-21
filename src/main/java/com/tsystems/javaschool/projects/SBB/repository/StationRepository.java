package com.tsystems.javaschool.projects.SBB.repository;

import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByStationName(String stationName);
}
