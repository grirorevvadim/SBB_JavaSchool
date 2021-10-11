package com.tsystems.javaschool.projects.SBB.service.implementation;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import com.tsystems.javaschool.projects.SBB.repository.TicketRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.TicketService;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public TicketDTO createTicket(TicketDTO ticket) {
        Ticket entity = new Ticket();
        BeanUtils.copyProperties(ticket, entity);
        entity.setUserId(ticket.getUserId());
        entity.setTicketId(utils.generateId(30));

        Ticket ticketEntity = ticketRepository.save(entity);
        TicketDTO resultTicket = new TicketDTO();
        BeanUtils.copyProperties(ticketEntity, resultTicket);
        return resultTicket;
    }

    @Override
    public TicketDTO getTicketByTicketId(String id) {

        TicketDTO ticketDTO = new TicketDTO();
        Ticket ticketEntity = ticketRepository.findByTicketId(id);

        if (ticketEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");
        BeanUtils.copyProperties(ticketEntity, ticketDTO);

        return ticketDTO;
    }

    @Override
    public TicketDTO updateTicket(String id, TicketDTO ticket) {
        TicketDTO resultTicket = new TicketDTO();
        Ticket ticketEntity = ticketRepository.findByTicketId(id);
        if (ticketEntity == null) throw new RuntimeException("Ticket with id: " + id + " is not found");
        if (userRepository.findByUserId(ticket.getUserId()) == null)
            throw new RuntimeException("User with id: " + ticket.getUserId() + " is not found");
        ticketEntity.setUserId(ticket.getUserId());

        Ticket updatedEntity = ticketRepository.save(ticketEntity);
        BeanUtils.copyProperties(updatedEntity, resultTicket);
        return resultTicket;
    }

    @Override
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