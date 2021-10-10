package com.tsystems.javaschool.projects.SBB.service.implementation;

import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    Utils utils;

    @Override
    public TrainDTO createTrain(TrainDTO train) {
        Train entity = new Train();
        BeanUtils.copyProperties(train, entity);
        entity.setTrainId(utils.generateId(30));
        //add check existence
        entity.setArrivalId(train.getArrivalId());
        entity.setDepartureId(train.getDepartureId());
        entity.setScheduleId(train.getScheduleId());
        entity.setAllSeatsNumber(train.getAllSeatsNumber());
        entity.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        entity.setTrainType(TrainType.Regional);

        Train requestEntity = trainRepository.save(entity);
        TrainDTO resultRequest = new TrainDTO();
        BeanUtils.copyProperties(requestEntity, resultRequest);
        return resultRequest;
    }

    @Override
    public TrainDTO getTrainByTrainId(String id) {
        TrainDTO train = new TrainDTO();
        Train trainEntity = trainRepository.findByTrainId(id);

        if (trainEntity == null) throw new RuntimeException("Train with id: " + id + " is not found");
        BeanUtils.copyProperties(trainEntity, train);

        return train;
    }

    @Override
    public TrainDTO updateTrain(String id, TrainDTO train) {
        TrainDTO resultTrain = new TrainDTO();
        Train trainEntity = trainRepository.findByTrainId(id);
        if (trainEntity == null) throw new RuntimeException("Train with id: " + id + " is not found");
        //if(userRepository.findByUserId(train.g())==null) throw new RuntimeException("Train with id: " + train.getTrainId() + " is not found");
        trainEntity.setArrivalId(train.getArrivalId());
        trainEntity.setDepartureId(train.getDepartureId());
        trainEntity.setAllSeatsNumber(train.getAllSeatsNumber());
        trainEntity.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        trainEntity.setTrainType(train.getTrainType());
        trainEntity.setScheduleId(train.getScheduleId());


        Train updatedEntity = trainRepository.save(trainEntity);
        BeanUtils.copyProperties(updatedEntity, resultTrain);
        return resultTrain;
    }

    @Override
    public String deleteTrain(String id) {
        Train resultEntity = trainRepository.findByTrainId(id);

        if (resultEntity == null) throw new RuntimeException("Train with id: " + id + " is not found");

        String result;
        trainRepository.delete(resultEntity);
        resultEntity = trainRepository.findByTrainId(id);
        if (resultEntity != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("Train with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }
}
