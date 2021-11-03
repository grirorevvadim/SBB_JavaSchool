package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
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
    public List<ScheduleDTO> searchTrains(String stationName, TrainDTO trainDTO) {
        StationDTO station = stationService.getStationByStationName(stationName);
        return filterScheduleByStation(station);
        //  var result2 = filterScheduleByTrain(trainDTO, result);
    }


    public List<ScheduleDTO> filterScheduleByDate(List<ScheduleDTO> resultList, String departureDate) {
        List<ScheduleDTO> filteredList = new ArrayList<>();
        for (ScheduleDTO schedule : resultList) {
            if (schedule.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(schedule);
        }
        return filteredList;
    }

    private List<ScheduleDTO> filterScheduleByTrain(TrainDTO trainDTO, List<ScheduleDTO> result) {
        List<ScheduleDTO> dtoList = new ArrayList<>();

        for (ScheduleDTO schedule : result) {
            if (trainDTO.getId().equals(schedule.getTrainId().getId()))
                dtoList.add(schedule);
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

    public List<ScheduleDTO> searchStationSchedule(String stationName, TrainDTO trainDTO) {
        List<ScheduleDTO> res = new ArrayList<>();
        var scheduleList = scheduleRepository.findByStation(stationRepository.findByStationName(stationName));
        for (Schedule schedule : scheduleList) {
            if (isScheduleContainsStations(schedule, trainDTO.getDepartureName(), trainDTO.getArrivalName()))
                res.add(scheduleMapper.mapToDto(schedule));
        }
        return res;
    }


    private boolean isScheduleContainsStations(Schedule schedule, String stationA, String stationB) {
        var schedules = scheduleRepository.findByTrain(schedule.getTrain());
        boolean res = false;
        for (Schedule dep : schedules) {
            if (dep.getStation().getStationName().equals(stationA)) {
                for (Schedule arr : schedules) {
                    if (arr.getStation().getStationName().equals(stationB)) {
                        res = true;
                        break;
                    }
                }
            }
        }
        return res;
    }

    public List<ScheduleDTO> getSchedulesByTrainNumber(String trainNumber) {
        var schedules = scheduleRepository.findByTrain(trainRepository.findByTrainNumber(trainNumber));
        List<ScheduleDTO> resList = new ArrayList<>();
        for (Schedule s : schedules)
            resList.add(scheduleMapper.mapToDto(s));
        return resList;
    }

    public ArrayList<List<ScheduleDTO>> getPagedSchedules(List<ScheduleDTO> schedules) {
        ArrayList<List<ScheduleDTO>> pagedSchedules = new ArrayList<>();
        //ArrayList<ScheduleDTO> currentSchedule = new ArrayList<>();
        String firstStation = schedules.get(0).getStation().getStationName();
        int start = 0;
        for (int i = 1; i < schedules.size(); i++) {
            if (schedules.get(i).getStation().getStationName().equals(firstStation) | i == (schedules.size() - 1)) {
                pagedSchedules.add(schedules.subList(start, i));
                start = i;
            }
        }
        return pagedSchedules;
    }

}
