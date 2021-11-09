package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TicketMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleService scheduleService;
    private final TrainMapper trainMapper;
    private final TicketMapper ticketMapper;
    private final UserService userService;
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

        Ticket ticketEntity = ticketRepository.getById(id);
        return ticketMapper.mapToDto(ticketEntity);
    }


    public TicketDTO updateTicket(Long id, TicketDTO dto) {
        Ticket ticket = ticketMapper.mapToEntity(dto);
        Ticket updatedEntity = ticketRepository.save(ticket);
        return ticketMapper.mapToDto(updatedEntity);
    }

    @Transactional
    public void deleteTicket(Long id) {
        Ticket resultEntity = ticketRepository.getById(id);
        ticketRepository.delete(resultEntity);
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
        var ticketList = ticketRepository.findByTrain(trainMapper.mapToEntity(ticketDTO.getTrain()));
        for (Ticket ticket : ticketList) {
            if (ticket.getTicketOwner().getEmail().equals(ticketDTO.getTicketOwner().getEmail())) {
                res = true;
                break;
            }
        }
        return res;
    }
}
