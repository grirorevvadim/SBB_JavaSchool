package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TicketMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScheduleService scheduleService;
    private final TicketMapper ticketMapper;
    private final ScheduleMapper scheduleMapper;
    private final TrainMapper trainMapper;
    private final TrainService trainService;
    private final UserService userService;
    private final UserMapper userMapper;

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

        TicketDTO ticketDTO = new TicketDTO();
        Ticket ticketEntity = ticketRepository.getById(id);

        if (ticketEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");
        BeanUtils.copyProperties(ticketEntity, ticketDTO);

        return ticketDTO;
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
        ticketDTO.setTicketOwner(userMapper.mapToEntity(user));
        ticketDTO.setTrain(departureSchedule.getTrainId());
        ticketDTO.setDepartureSchedule(scheduleMapper.mapToEntity(departureSchedule));
        ticketDTO.setArrivalSchedule(scheduleMapper.mapToEntity(arrivalSchedule));
        return ticketDTO;
    }
}
