package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TicketMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
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
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final TrainMapper trainMapper;
    private final TrainService trainService;
    private final Utils utils;

    @Transactional
    public TicketDTO createTicket(TicketDTO ticket) {
        var entity = ticketMapper.mapToEntity(ticket);
        entity.setTicketId(utils.generateId(30));
        var ticketEntity = ticketRepository.save(entity);
        trainService.decreaseAvailableSeatsAmount(trainMapper.mapToDto(ticket.getTrain()));
        return ticketMapper.mapToDto(ticketEntity);
    }

    @Transactional(readOnly = true)
    public TicketDTO getTicketByTicketId(String id) {

        TicketDTO ticketDTO = new TicketDTO();
        Ticket ticketEntity = ticketRepository.findByTicketId(id);

        if (ticketEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");
        BeanUtils.copyProperties(ticketEntity, ticketDTO);

        return ticketDTO;
    }


    public TicketDTO updateTicket(String id, TicketDTO ticket) {
        TicketDTO resultTicket = new TicketDTO();
        Ticket ticketEntity = ticketRepository.findByTicketId(id);
        if (ticketEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");
        if (userRepository.findByUserId(ticket.getTicketOwner().getUserId()) == null)
            throw new RuntimeException("User with id: " + ticket.getTicketOwner() + " is not found");
        ticketEntity.setTicketOwner(ticket.getTicketOwner());

        Ticket updatedEntity = ticketRepository.save(ticketEntity);
        BeanUtils.copyProperties(updatedEntity, resultTicket);
        return resultTicket;
    }

    @Transactional
    public String deleteTicket(String id) {
        Ticket resultEntity = ticketRepository.findByTicketId(id);

        if (resultEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");

        String result;
        ticketRepository.delete(resultEntity);
        resultEntity = ticketRepository.findByTicketId(id);
        if (resultEntity != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("Ticket with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }
}
