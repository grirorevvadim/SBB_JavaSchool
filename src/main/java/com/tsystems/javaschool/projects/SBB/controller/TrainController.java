package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("trains")
public class TrainController {

    private final TrainService trainService;

    @GetMapping(path = "/{id}")
    public TrainDTO getTrain(@PathVariable String id, Model model) {
        return trainService.getTrainByTrainId(id);
    }

    @PostMapping
    public TrainDTO postTrain(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return trainService.createTrain(trainDTO);
    }

//    @PutMapping(path = "/{id}")
//    public TrainRest updateTrain(@PathVariable String id, @RequestBody TrainDetailsModel trainDetails) {
//        TrainRest trainRest = new TrainRest();
//        TrainDTO trainDTO = new TrainDTO();
//        BeanUtils.copyProperties(trainDetails, trainDTO);
//        trainDTO = trainService.updateTrain(id, trainDTO);
//        BeanUtils.copyProperties(trainDTO, trainRest);
//        return trainRest;
//    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteTrain(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(trainService.deleteTrain(id));
        return status;
    }
}
