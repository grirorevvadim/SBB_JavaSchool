package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;

public interface TrainService {

    TrainDTO createTrain(TrainDTO request);

    TrainDTO getTrainByTrainId(String id);

    TrainDTO updateTrain(String id, TrainDTO request);

    String deleteTrain(String id);
}
