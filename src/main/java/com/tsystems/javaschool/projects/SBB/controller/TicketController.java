package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.TicketService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
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
    private final TrainService trainService;
    private final TrainMapper trainMapper;
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @GetMapping(path = "/{id}")
    public TicketDTO getTicket(@PathVariable String id, Model model) {
        return ticketService.getTicketByTicketId(id);
    }

    @GetMapping("/ticket-info")
    public String getTicketInfo(@ModelAttribute(name = "ticket") TicketDTO ticketDTO) {
        return "ticket-info";
    }

    @PostMapping
    public TicketDTO postTicket(@ModelAttribute(name = "ticket") TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @GetMapping("/create")
    public String getTicketForm(@RequestParam(name = "departureId") String departureId, @RequestParam(name = "arrivalId") String arrivalId, @ModelAttribute(name = "ticket") TicketDTO ticketDTO, Model model) {
        var departureSchedule = scheduleService.getScheduleByScheduleId(departureId);
        var arrivalSchedule = scheduleService.getScheduleByScheduleId(arrivalId);
        ticketDTO.setTrain(departureSchedule.getTrainId());
        ticketDTO.setDepartureSchedule(scheduleMapper.mapToEntity(departureSchedule));
        ticketDTO.setArrivalSchedule(scheduleMapper.mapToEntity(arrivalSchedule));
        return "create-ticket";
    }

    @PostMapping("/register")
    public String registerTicket(@Valid @ModelAttribute(name = "ticket") TicketDTO ticketDTO, BindingResult result) {
        if (result.hasErrors()) return "create-ticket";
        ticketService.createTicket(ticketDTO);
        return "redirect:/tickets/ticket-info";
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
    public OperationStatus deleteTicket(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(ticketService.deleteTicket(id));
        return status;
    }
}
