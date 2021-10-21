package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;

@Mapper
public class TicketMapper {
    public TicketDTO mapToDto(Ticket ticket) {
        var dto = new TicketDTO();
        dto.setTrain(ticket.getTrain());
        dto.setId(ticket.getId());
        dto.setTicketOwner(ticket.getTicketOwner());
        dto.setDepartureSchedule(ticket.getDepartureSchedule());
        dto.setArrivalSchedule(ticket.getArrivalSchedule());
        return dto;
    }

    public Ticket mapToEntity(TicketDTO dto) {
        var ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setTicketOwner(dto.getTicketOwner());
        ticket.setTrain(dto.getTrain());
        ticket.setDepartureSchedule(dto.getDepartureSchedule());
        ticket.setArrivalSchedule(dto.getArrivalSchedule());
        return ticket;
    }
}
