package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.TicketService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TrainService trainService;
    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping(path = "/{id}")
    public TicketDTO getTicket(@PathVariable Long id, Model model) {
        return ticketService.getTicketByTicketId(id);
    }

    @PostMapping
    public TicketDTO postTicket(@ModelAttribute(name = "ticket") TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @GetMapping()
    public String getTicketForm(@RequestParam(name = "departureId") Long departureId,
                                @RequestParam(name = "arrivalId") Long arrivalId,
                                @ModelAttribute(name = "ticket") TicketDTO ticketDTO,
                                Model model, Principal principal) {
        UserDTO userDTO = userService.findUserByEmail(principal.getName());
        model.addAttribute("departureId", departureId);
        model.addAttribute("arrivalId", arrivalId);
        model.addAttribute("errorMessage", "");
        model.addAttribute("user", userDTO);
        model.addAttribute("loggedUser", principal.getName());
        ticketDTO.setTicketOwner(userDTO);
        return "create-ticket";
    }

    @PostMapping("/register")
    public String registerTicket(@RequestParam(name = "departureId") Long departureId,
                                 @RequestParam(name = "arrivalId") Long arrivalId,
                                 @Valid @ModelAttribute(name = "ticket") TicketDTO ticketDTO,
                                 Principal principal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("loggedUser", principal.getName());
            return "create-ticket";
        }
        ticketDTO = ticketService.fillTicketData(departureId, arrivalId, ticketDTO);

        if (ticketService.isUserRegistered(ticketDTO)) {
            model.addAttribute("departureId", departureId);
            model.addAttribute("arrivalId", arrivalId);
            model.addAttribute("errorMessage", "User has already been registered to the train");
            model.addAttribute("loggedUser", principal.getName());
            return "create-ticket";
        }
        int price = trainService.getPrice(ticketDTO.getTrain().getTrainNumber(), ticketDTO.getDepartureSchedule().getStation().getStationName(), ticketDTO.getArrivalSchedule().getStation().getStationName());
        ticketDTO.setPrice(price);
        if (ticketDTO.getTicketOwner().getWallet() < price) {
            log.warn("User doesn't have enough cash for the ticket");
            model.addAttribute("errorMessage", "User doesn't have enough cash for the ticket");
            model.addAttribute("departureId", departureId);
            model.addAttribute("arrivalId", arrivalId);
            model.addAttribute("loggedUser", principal.getName());
            return "create-ticket";
        }
        if (ticketDTO.getDepartureSchedule().getAvailableSeatsNumber() <= 0) {
            log.warn("All available places have already been booked");
            model.addAttribute("errorMessage", "All available places have already been booked");
            model.addAttribute("departureId", departureId);
            model.addAttribute("arrivalId", arrivalId);
            model.addAttribute("loggedUser", principal.getName());
            return "create-ticket";
        }

        ticketDTO = ticketService.createTicket(ticketDTO);
        scheduleService.addUserToSchedule(ticketDTO);
        userService.decreaseWalletAmount(ticketDTO.getTicketOwner(), ticketDTO.getPrice());
        scheduleService.decreaseAvailableSeatsAmount(ticketDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("ticket", ticketDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "ticket-info";
    }

    @GetMapping("/all")
    public String getTicketSearchForm(@ModelAttribute(name = "ticket") TicketDTO ticketDTO,
                                      Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "search-tickets";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable("id") long id) {
        TicketDTO ticket = ticketService.getTicketByTicketId(id);
        ticketService.deleteTicket(id);
        scheduleService.removeUserFromSchedule(ticket);
        userService.increaseWalletAmount(ticket.getTicketOwner(), ticket.getPrice());
        scheduleService.increaseAvailableSeatsAmount(ticket);
        return "redirect:/tickets/info";
    }

    @GetMapping("/bytrain")
    public String ticketSearchByTrain(@ModelAttribute(name = "ticket") TicketDTO ticketDTO,
                                      Model model, Principal principal) {
        TrainDTO train = trainService.getTrainByTrainNumber(ticketDTO.getTrain().getTrainNumber());
        var tickets = ticketService.getTicketsByTrain(train);
        model.addAttribute("tickets", tickets);
        model.addAttribute("loggedUser", principal.getName());
        return "tickets";
    }

    @GetMapping("/info")
    public String userTickets(Model model, Principal principal) {
        UserDTO userDTO = userService.findUserByEmail(principal.getName());
        var tickets = ticketService.findTicketsByUser(userDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("tickets", tickets);
        model.addAttribute("loggedUser", principal.getName());
        model.addAttribute("timeLimit", LocalDateTime.now());
        return "my-tickets";
    }


//    @DeleteMapping(path = "/{id}")
//    public void deleteTicket(@PathVariable Long id) {
//        ticketService.deleteTicket(id);
//    }
}
