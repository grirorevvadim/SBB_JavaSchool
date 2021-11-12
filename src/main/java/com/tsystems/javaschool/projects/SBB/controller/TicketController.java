package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TicketDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.TicketService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
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
    public String getTicketForm(@RequestParam(name = "departureId") Long departureId, @RequestParam(name = "arrivalId") Long arrivalId, @ModelAttribute(name = "ticket") TicketDTO ticketDTO, Model model, Principal principal) {
        UserDTO userDTO = userService.findUserByEmail(principal.getName());
        model.addAttribute("departureId", departureId);
        model.addAttribute("arrivalId", arrivalId);
        model.addAttribute("errorMessage", "");
        model.addAttribute("user", userDTO);
        ticketDTO.setTicketOwner(userDTO);
        return "create-ticket";
    }

    @PostMapping("/register")
    public String registerTicket(@RequestParam(name = "departureId") Long departureId, @RequestParam(name = "arrivalId") Long arrivalId, @Valid @ModelAttribute(name = "ticket") TicketDTO ticketDTO, BindingResult result, Model model) {
        if (result.hasErrors()) return "create-ticket";
        ticketDTO = ticketService.fillTicketData(departureId, arrivalId, ticketDTO);

        if (ticketService.isUserRegistered(ticketDTO)) {
            model.addAttribute("departureId", departureId);
            model.addAttribute("arrivalId", arrivalId);
            model.addAttribute("errorMessage", "User has already been registered to the train");
            return "create-ticket";
        }
        int price = trainService.getPrice(ticketDTO.getTrain().getTrainNumber(), ticketDTO.getDepartureSchedule().getStation().getStationName(), ticketDTO.getArrivalSchedule().getStation().getStationName());
        ticketDTO.setPrice(price);
        if (ticketDTO.getTicketOwner().getWallet() < price) {
            System.out.println("User doesn't have enough cash for the ticket");
            model.addAttribute("errorMessage", "User doesn't have enough cash for the ticket");
            model.addAttribute("departureId", departureId);
            model.addAttribute("arrivalId", arrivalId);
            return "create-ticket";
        }
        ticketDTO = ticketService.createTicket(ticketDTO);
        userService.decreaseWalletAmount(ticketDTO.getTicketOwner(), ticketDTO.getPrice());
        //trainService.decreaseAvailableSeatsAmount(ticketDTO.getTrain());
        scheduleService.decreaseAvailableSeatsAmount(ticketDTO);

        model.addAttribute("ticket", ticketDTO);
        return "ticket-info";
    }

    @GetMapping("/all")
    public String getTicketSearchForm(@ModelAttribute(name = "ticket") TicketDTO ticketDTO, Model model) {
        return "search-tickets";
    }

    @GetMapping("/bytrain")
    public String ticketSearchByTrain(@ModelAttribute(name = "ticket") TicketDTO ticketDTO, Model model) {
        TrainDTO train = trainService.getTrainByTrainNumber(ticketDTO.getTrain().getTrainNumber());
        List<TicketDTO> tickets = ticketService.getTicketsByTrain(train);
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @GetMapping("/info")
    public String userTickets(Model model, Principal principal) {
        UserDTO userDTO = userService.findUserByEmail(principal.getName());
        List<TicketDTO> tickets = ticketService.findTicketsByUser(userDTO);
        model.addAttribute("tickets", tickets);
        return "my-tickets";
    }


    @DeleteMapping(path = "/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}
