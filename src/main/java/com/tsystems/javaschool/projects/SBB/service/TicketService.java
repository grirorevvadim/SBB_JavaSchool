package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;

public interface TicketService {
    TicketDTO createTicket(TicketDTO ticket);

    TicketDTO getTicketByTicketId(String id);

    TicketDTO updateTicket(String id, TicketDTO ticket);

    String deleteTicket(String id);
}
