package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.*;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;
    private final ScheduleMapper scheduleMapper;
    private final StationService stationService;
    private final TrainMapper trainMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;


    @Transactional
    public void createSchedule(ScheduleDTO scheduleDTO) {
        var schedule = scheduleMapper.mapToEntity(scheduleDTO);
        scheduleRepository.save(schedule);
        notifyConsumer();
    }

    public ScheduleDTO getScheduleByScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(scheduleMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Schedule with id= " + scheduleId + " is not found"));
    }

    @Transactional(readOnly = true)
    public List<ScheduleDTO> filterScheduleByStation(StationDTO station) {
        Station stationDeparture = stationRepository.findByStationName(station.getStationName());
        List<ScheduleDTO> result = new ArrayList<>();
        List<Schedule> byStation = scheduleRepository.findByStation(stationDeparture);

        for (Schedule schedule : byStation) {
            result.add(scheduleMapper.mapToDto(schedule));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<ScheduleDTO> searchTrains(String stationName, TrainDTO trainDTO) {
        StationDTO station = stationService.getStationByStationName(stationName);
        return filterScheduleByStation(station);
    }


    public List<ScheduleDTO> filterScheduleByDate(List<ScheduleDTO> resultList, String departureDate) {
        List<ScheduleDTO> filteredList = new ArrayList<>();
        for (ScheduleDTO schedule : resultList) {
            if (schedule.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(schedule);
        }
        return filteredList;
    }

    public List<Schedule> filterBoardByDate(List<Schedule> resultList, String departureDate) {
        List<Schedule> filteredList = new ArrayList<>();
        for (Schedule board : resultList) {
            if (board.getArrivalDateTime().toLocalDate().toString().equals(departureDate))
                filteredList.add(board);
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

    @Transactional(readOnly = true)
    public List<ScheduleDTO> getSchedulesByStation(StationDTO stationDTO) {
        var schedules = scheduleRepository.findByStation(stationRepository.findByStationName(stationDTO.getStationName()));
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        for (Schedule schedule : schedules) {
            schedulesDTO.add(scheduleMapper.mapToDto(schedule));
        }
        return schedulesDTO;
    }

    @Transactional(readOnly = true)
    public List<ScheduleDTO> searchStationSchedule(String stationName, TrainDTO trainDTO) {
        List<ScheduleDTO> res = new ArrayList<>();
        var scheduleList = scheduleRepository.findByStation(stationRepository.findByStationName(stationName));
        for (Schedule schedule : scheduleList) {
            if (isScheduleContainsStations(schedule, trainDTO.getDepartureName(), trainDTO.getArrivalName()))
                res.add(scheduleMapper.mapToDto(schedule));
        }
        return res;
    }

    @Transactional(readOnly = true)
    public List<ScheduleDTO> searchArrivalSchedule(String arrivalName, List<ScheduleDTO> departure) {
        List<ScheduleDTO> res = new ArrayList<>();
        var scheduleList = scheduleRepository.findAll();
        for (ScheduleDTO scheduleDTO : departure) {
            for (int j = 0; j < scheduleList.size(); j++) {
                if (scheduleList.get(j).getId() == scheduleDTO.getId()) {
                    for (int r = j; r < scheduleList.size(); r++) {
                        if (scheduleList.get(r).getStation().getStationName().equals(arrivalName))
                            res.add(scheduleMapper.mapToDto(scheduleList.get(r)));
                    }
                }
            }
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
        //for (int i = 1; i < schedules.size(); i++) {
        for (int i = 1; i < schedules.size(); i++) {
            if (schedules.get(i).getStation().getStationName().equals(firstStation)) {
                pagedSchedules.add(schedules.subList(start, i));
                start = i;
            }
            if (i == (schedules.size() - 1)) {
                pagedSchedules.add(schedules.subList(start, i + 1));
            }
        }
        return pagedSchedules;
    }

    @Transactional
    public void deleteSchedule(Long id) {
        //ScheduleDTO dto = getScheduleByScheduleId(id);
        //scheduleRepository.delete(scheduleMapper.mapToEntity(dto));
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()) throw new EntityNotFoundException("Schedule with id " + id + " is not found");
        scheduleRepository.delete(schedule.get());
        notifyConsumer();
    }

    @Transactional
    public Schedule updateSchedule(Schedule schedule) {
        ScheduleDTO uSchedule = getScheduleByScheduleId(schedule.getId());
        Schedule updatedSchedule = scheduleMapper.mapToEntity(uSchedule);
        StationDTO stationDto = stationService.getStationByStationName(schedule.getStation().getStationName());
        if (schedule.getStation() != null) updatedSchedule.setStation(stationMapper.mapToEntity(stationDto));
        if (schedule.getArrivalDateTime() != null) updatedSchedule.setArrivalDateTime(schedule.getArrivalDateTime());
        scheduleRepository.save(updatedSchedule);
        notifyConsumer();
        return updatedSchedule;
    }

    @Transactional
    public void decreaseAvailableSeatsAmount(TicketDTO ticketDTO) {
        List<Schedule> affected = getAffectedSchedules(ticketDTO);
        for (Schedule schedule : affected) {
            schedule.setAvailableSeatsNumber(schedule.getAvailableSeatsNumber() - 1);
            scheduleRepository.save(schedule);
        }
    }

    private List<Schedule> getAffectedSchedules(TicketDTO ticketDTO) {
        Train train = trainMapper.mapToEntity(ticketDTO.getTrain());
        List<Schedule> schedules = scheduleRepository.findByTrain(train);
        int indexDep = 0;
        int indexArr = 0;
        ZonedDateTime dep = ZonedDateTime.of(ticketDTO.getDepartureSchedule().getArrivalDateTime(), ZoneId.of("Europe/Moscow"));
        ZonedDateTime arr = ZonedDateTime.of(ticketDTO.getArrivalSchedule().getArrivalDateTime(), ZoneId.of("Europe/Moscow"));
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getArrivalDateTime().isEqual(dep)) {
                indexDep = i;
            }
            if (schedules.get(i).getArrivalDateTime().isEqual(arr)) {
                indexArr = i;
                break;
            }
        }
        return schedules.subList(indexDep, indexArr);
    }

    @Transactional
    public void addUserToSchedule(TicketDTO ticketDTO) {
        List<Schedule> affected = getAffectedSchedules(ticketDTO);
        var userDto = userService.findUserByEmail(ticketDTO.getTicketOwner().getEmail());
        var user = userMapper.mapToEntity(userDto);

        for (Schedule schedule : affected) {
            schedule.getUsersList().add(user);
            scheduleRepository.save(schedule);
        }

    }

    public void notifyConsumer() {
        var schedules = scheduleRepository.findAll();
        var filteredSchedules = filterBoardByDate(schedules, LocalDate.now().toString());
        var board = new ArrayList<>();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Schedule s : filteredSchedules) {
            board.add(new BoardDTO(s.getTrain().getTrainNumber(), s.getArrivalDateTime().format(formatter), s.getStation().getStationName()));
        }
        rabbitTemplate.convertAndSend("schedules", board);
    }

}
