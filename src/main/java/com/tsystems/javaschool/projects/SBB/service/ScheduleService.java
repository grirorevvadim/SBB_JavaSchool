package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final Utils utils;
    private final ScheduleMapper scheduleMapper;
    private final StationMapper stationMapper;
    private final RootService rootService;
    private final StationService stationService;
    private final TrainService trainService;


    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        var schedule = scheduleMapper.mapToEntity(scheduleDTO);
        //schedule.setArrivalDateTime();
        return null;
    }

    private LocalDateTime getArrivalDateTime(Schedule schedule) {
        return null;
    }

    @Transactional
    public List<Schedule> filterScheduleByStation(StationDTO station) {
        Station stationDeparture = stationRepository.findByStationName(station.getStationName());

        List<Schedule> byStation = scheduleRepository.findByStation(stationDeparture);
        return byStation;
    }

    @Transactional
    public List<ScheduleDTO> searchTrains(String stationName, String date) {
        StationDTO station = stationService.getStationByStationName(stationName);

        List<Schedule> result = filterScheduleByStation(station);
        List<ScheduleDTO> filteredList = filterScheduleByDate(result, station, date);
        return filteredList;
    }

    private List<ScheduleDTO> filterScheduleByDate(List<Schedule> resultList, StationDTO departure, String departureDate) {
        List<ScheduleDTO> filteredList = new ArrayList<>();
        for (Schedule schedule : resultList) {
            if (schedule.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(scheduleMapper.mapToDto(schedule));
        }
        return filteredList;
    }

    private List<Schedule> filterScheduleByTrain(List<Train> trainList, List<Schedule> result) {
        List<Schedule> dtoList = new ArrayList<>();
        for (Train train : trainList) {
            for (Schedule schedule : result) {
                if (train.getTrainId().equals(schedule.getTrain_id().getTrainId()))
                    dtoList.add(schedule);
            }
        }
        return dtoList;
    }
}
