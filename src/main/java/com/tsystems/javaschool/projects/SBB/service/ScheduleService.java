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
    private final ScheduleMapper scheduleMapper;
    private final StationService stationService;


    @Transactional
    public void createSchedule(ScheduleDTO scheduleDTO) {
        var schedule = scheduleMapper.mapToEntity(scheduleDTO);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public ScheduleDTO getScheduleByScheduleId(Long scheduleId) {
        var schedule = scheduleRepository.getById(scheduleId);
        return scheduleMapper.mapToDto(schedule);
    }

    @Transactional
    public List<ScheduleDTO> filterScheduleByStation(StationDTO station) {
        Station stationDeparture = stationRepository.findByStationName(station.getStationName());
        List<ScheduleDTO> result = new ArrayList<>();
        List<Schedule> byStation = scheduleRepository.findByStation(stationDeparture);

        for (Schedule schedule : byStation) {
            result.add(scheduleMapper.mapToDto(schedule));
        }
        return result;
    }

    @Transactional
    public List<ScheduleDTO> searchTrains(String stationName, String date) {
        StationDTO station = stationService.getStationByStationName(stationName);

        var result = filterScheduleByStation(station);
        var filteredList = filterScheduleByDate(result, station, date);
        return filteredList;
    }

    private List<ScheduleDTO> filterScheduleByDate(List<ScheduleDTO> resultList, StationDTO departure, String departureDate) {
        List<ScheduleDTO> filteredList = new ArrayList<>();
        for (ScheduleDTO schedule : resultList) {
            if (schedule.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(schedule);
        }
        return filteredList;
    }

    private List<Schedule> filterScheduleByTrain(List<Train> trainList, List<Schedule> result) {
        List<Schedule> dtoList = new ArrayList<>();
        for (Train train : trainList) {
            for (Schedule schedule : result) {
                if (train.getId().equals(schedule.getTrain_id().getId()))
                    dtoList.add(schedule);
            }
        }
        return dtoList;
    }

    public List<ScheduleDTO> getSchedulesByStation(StationDTO stationDTO) {
        var schedules = scheduleRepository.findByStation(stationRepository.findByStationName(stationDTO.getStationName()));
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        for (Schedule schedule : schedules) {
            schedulesDTO.add(scheduleMapper.mapToDto(schedule));
        }
        return schedulesDTO;
    }
}
