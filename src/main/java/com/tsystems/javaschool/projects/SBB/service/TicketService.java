package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleService scheduleService;
    private final TicketMapper ticketMapper;
    private final UserService userService;


    @Transactional
    public TicketDTO createTicket(TicketDTO ticket) {
        var entity = ticketMapper.mapToEntity(ticket);
        var ticketEntity = ticketRepository.save(entity);
        //  userRepository.save(ticketEntity.getTicketOwner());
        //  trainService.decreaseAvailableSeatsAmount(trainMapper.mapToDto(ticket.getTrain()));
        return ticketMapper.mapToDto(ticketEntity);
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
        var user = userService.findUserByEmail(ticketDTO.getTicketOwner().getEmail());
        ticketDTO.setTicketOwner(user);
        ticketDTO.setTrain(departureSchedule.getTrainId());
        ticketDTO.setDepartureSchedule(departureSchedule);
        ticketDTO.setArrivalSchedule(arrivalSchedule);
        return ticketDTO;
    }
}
