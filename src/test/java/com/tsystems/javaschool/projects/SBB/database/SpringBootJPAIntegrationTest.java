package com.tsystems.javaschool.projects.SBB.database;

import com.tsystems.javaschool.projects.SBB.SbbApplication;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbbApplication.class)
public class SpringBootJPAIntegrationTest {
    @Autowired
    private StationRepository stationRepository;

    @Test
    public void stationSaveAndGetTest() {
        Station station = stationRepository.save(new Station("TestStation"));
        Optional<Station> foundStation = stationRepository.findById(station.getId());

        assertNotNull(foundStation);
        foundStation.ifPresent(value -> assertEquals(station.getStationName(), value.getStationName()));
    }
}
