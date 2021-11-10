package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Ticket;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class TicketMapper {
    private final TrainMapper trainMapper;
    private final UserMapper userMapper;
    private final ScheduleMapper scheduleMapper;

    public TicketDTO mapToDto(Ticket ticket) {
        var dto = new TicketDTO();
        dto.setTrain(trainMapper.mapToDto(ticket.getTrain()));
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setTicketOwner(userMapper.mapToDto(ticket.getTicketOwner()));
        dto.setDepartureSchedule(scheduleMapper.mapToDto(ticket.getDepartureSchedule()));
        dto.setArrivalSchedule(scheduleMapper.mapToDto(ticket.getArrivalSchedule()));
        return dto;
    }

    public Ticket mapToEntity(TicketDTO dto) {
        var ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setPrice(dto.getPrice());
        ticket.setTicketOwner(userMapper.mapToEntity(dto.getTicketOwner()));
        ticket.setTrain(trainMapper.mapToEntity(dto.getTrain()));
        ticket.setDepartureSchedule(scheduleMapper.mapToEntity(dto.getDepartureSchedule()));
        ticket.setArrivalSchedule(scheduleMapper.mapToEntity(dto.getArrivalSchedule()));
        return ticket;
    }
}
