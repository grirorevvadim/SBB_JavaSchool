package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TicketMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    private final TrainMapper trainMapper;
    private final TicketMapper ticketMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TrainService trainService;


    @Transactional
    public TicketDTO createTicket(TicketDTO ticket) {
        var entity = ticketMapper.mapToEntity(ticket);
        Integer price = trainService.getPrice(ticket.getTrain().getTrainNumber(), ticket.getDepartureSchedule().getStation().getStationName(), ticket.getArrivalSchedule().getStation().getStationName());
        entity.setPrice(price);
        var ticketEntity = ticketRepository.save(entity);
        //  userRepository.save(ticketEntity.getTicketOwner());
        //  trainService.decreaseAvailableSeatsAmount(trainMapper.mapToDto(ticket.getTrain()));
        return ticketMapper.mapToDto(ticketEntity);
    }

    @Transactional
    public List<TicketDTO> getTicketsByTrain(TrainDTO dto) {
        Train train = trainMapper.mapToEntity(dto);
        List<TicketDTO> dtos = new ArrayList<>();
        List<Ticket> tickets = ticketRepository.getByTrain(train);

        for (Ticket ticket : tickets) {
            dtos.add(ticketMapper.mapToDto(ticket));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TicketDTO getTicketByTicketId(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id = " + id + " is not found"));
    }


    public TicketDTO updateTicket(Long id, TicketDTO dto) {
        Ticket ticket = ticketMapper.mapToEntity(dto);
        Ticket updatedEntity = ticketRepository.save(ticket);
        return ticketMapper.mapToDto(updatedEntity);
    }

    @Transactional
    public void deleteTicket(Long id) {
        Optional<Ticket> resultEntity = ticketRepository.findById(id);
        if (resultEntity.isEmpty()) throw new EntityNotFoundException("Ticket with id =" + id + " is not found");
        ticketRepository.delete(resultEntity.get());
    }

    @Transactional
    public TicketDTO fillTicketData(Long departureId, Long arrivalId, TicketDTO ticketDTO) {
        var departureSchedule = scheduleService.getScheduleByScheduleId(departureId);
        var arrivalSchedule = scheduleService.getScheduleByScheduleId(arrivalId);
        var train = trainService.getTrainByTrainId(departureSchedule.getTrainId().getId());
        var user = userService.findUserByEmail(ticketDTO.getTicketOwner().getEmail());
        ticketDTO.setTicketOwner(user);
        ticketDTO.setTrain(train);
        ticketDTO.setDepartureSchedule(departureSchedule);
        ticketDTO.setArrivalSchedule(arrivalSchedule);
        return ticketDTO;
    }

    @Transactional
    public boolean isUserRegistered(TicketDTO ticketDTO) {
        boolean res = false;
        var schedule = scheduleService.getScheduleByScheduleId(ticketDTO.getDepartureSchedule().getId());
        for (UserDTO user : schedule.getUsersList()) {
            if (user.getEmail().equals(ticketDTO.getTicketOwner().getEmail())) {
                res = true;
                break;
            }
        }
        return res;
    }

    public List<TicketDTO> findTicketsByUser(UserDTO userDTO) {
        List<Ticket> tickets = ticketRepository.findByTicketOwner(userRepository.findByEmail(userDTO.getEmail()));
        List<TicketDTO> dtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            dtos.add(ticketMapper.mapToDto(ticket));
        }
        return dtos;
    }
}
