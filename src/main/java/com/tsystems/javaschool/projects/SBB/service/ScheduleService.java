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

    public List<Schedule> filterScheduleByStation(StationDTO departure, StationDTO arrival) {
        Station stationDeparture = stationRepository.findByStationName(departure.getStationName());
        Station stationArrival = stationRepository.findByStationName(arrival.getStationName());
//        List<Schedule> byDeparture = scheduleRepository.findByStationId(stationDeparture.getStationId());
//        List<Schedule> byArrival = scheduleRepository.findByStationId(stationArrival.getStationId());

        List<Schedule> byDeparture = scheduleRepository.findByStation(stationDeparture);
        List<Schedule> byArrival = scheduleRepository.findByStation(stationArrival);
        byDeparture.addAll(byArrival);
        return byDeparture;
    }

    @Transactional
    public List<Schedule> searchTrains(TrainDTO trainDTO) {
        StationDTO departure = stationService.getStationByStationName(trainDTO.getDepartureName());
        StationDTO arrival = stationService.getStationByStationName(trainDTO.getArrivalName());

        // var rootDtoList = rootService.searchRoots(stationMapper.mapToEntity(departure), stationMapper.mapToEntity(arrival));
        //List<Train> trainList = trainService.searchTrainsByRoots(rootDtoList);
        List<Schedule> result = filterScheduleByStation(departure, arrival);
        //List<Schedule> resultList = filterScheduleByTrain(trainList, result);
        List<Schedule> filteredList = filterScheduleByDate(result, departure, trainDTO.getDepartureDate());
        //List<Schedule> finalResult = filterScheduleByDate()
        return filteredList;
    }

    private List<Schedule> filterScheduleByDate(List<Schedule> resultList, StationDTO departure, String departureDate) {
        List<Schedule> filteredList = new ArrayList<>();
        for (Schedule schedule : resultList) {
            if (schedule.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(schedule);
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
