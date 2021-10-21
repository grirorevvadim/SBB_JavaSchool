package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.TicketService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping(path = "/{id}")
    public TicketDTO getTicket(@PathVariable Long id, Model model) {
        return ticketService.getTicketByTicketId(id);
    }

    @PostMapping
    public TicketDTO postTicket(@ModelAttribute(name = "ticket") TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @GetMapping("/create")
    public String getTicketForm(@RequestParam(name = "departureId") Long departureId, @RequestParam(name = "arrivalId") Long arrivalId, @ModelAttribute(name = "ticket") TicketDTO ticketDTO, Model model) {
        model.addAttribute("departureId", departureId);
        model.addAttribute("arrivalId", arrivalId);
        return "create-ticket";
    }

    @PostMapping("/register")
    public String registerTicket(@RequestParam(name = "departureId") Long departureId, @RequestParam(name = "arrivalId") Long arrivalId, @Valid @ModelAttribute(name = "ticket") TicketDTO ticketDTO, BindingResult result, Model model) {
        if (result.hasErrors()) return "create-ticket";
        ticketDTO = ticketService.fillTicketData(departureId, arrivalId, ticketDTO);

        ticketService.createTicket(ticketDTO);
        model.addAttribute("ticket", ticketDTO);
        return "ticket-info";
    }

//    @PutMapping(path = "/{id}")
//    public TicketDTO updateRequest(@PathVariable String id, @RequestBody RequestDetailsModel requestDetails) {
//        RequestRest requestRest = new RequestRest();
//        TicketDTO ticketDTO = new TicketDTO();
//        BeanUtils.copyProperties(requestDetails, ticketDTO);
//        ticketDTO = ticketService.updateRequest(id, ticketDTO);
//        BeanUtils.copyProperties(ticketDTO, requestRest);
//        return requestRest;
//    }

    @DeleteMapping(path = "/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}
