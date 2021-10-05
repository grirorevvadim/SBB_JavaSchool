package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.shared.dataTransferObject.TrainDTO;

public interface TrainService {

    TrainDTO createTrain(TrainDTO request);

    TrainDTO getTrainByTrainId(String id);

    TrainDTO updateTrain(String id, TrainDTO request);

    String deleteTrain(String id);
}
