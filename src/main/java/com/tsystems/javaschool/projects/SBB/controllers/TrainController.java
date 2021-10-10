package com.tsystems.javaschool.projects.SBB.controllers;

import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.OperationStatus;
import com.tsystems.javaschool.projects.SBB.ui.models.request.TrainDetailsModel;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationName;
import com.tsystems.javaschool.projects.SBB.ui.models.response.TrainRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trains")
public class TrainController {

    @Autowired
    TrainService trainService;

    @GetMapping(path = "/{id}")
    public TrainRest getTrain(@PathVariable String id) {
        TrainRest trainRest = new TrainRest();
        TrainDTO trainDTO = trainService.getTrainByTrainId(id);
        BeanUtils.copyProperties(trainDTO, trainRest);
        return trainRest;
    }

    @PostMapping
    public TrainRest postTrain(@RequestBody TrainDetailsModel trainDetails) {
        TrainRest trainRest = new TrainRest();
        TrainDTO trainDTO = new TrainDTO();
        BeanUtils.copyProperties(trainDetails, trainDTO);
        TrainDTO createdTrain = trainService.createTrain(trainDTO);
        BeanUtils.copyProperties(createdTrain, trainRest);
        return trainRest;
    }

    @PutMapping(path = "/{id}")
    public TrainRest updateTrain(@PathVariable String id, @RequestBody TrainDetailsModel trainDetails) {
        TrainRest trainRest = new TrainRest();
        TrainDTO trainDTO = new TrainDTO();
        BeanUtils.copyProperties(trainDetails, trainDTO);
        trainDTO = trainService.updateTrain(id, trainDTO);
        BeanUtils.copyProperties(trainDTO, trainRest);
        return trainRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteTrain(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(trainService.deleteTrain(id));
        return status;
    }
}
