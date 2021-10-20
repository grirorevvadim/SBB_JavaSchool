package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final RootService rootService;
    private StationService stationService;
    private StationMapper stationMapper;
    private ScheduleService scheduleService;
    private final Utils utils;

    public TrainDTO createTrain(TrainDTO train) {
        Train entity = trainMapper.mapToEntity(train);
        entity.setTrainId(utils.generateId(30));
        //add check existence
        entity.setScheduleList(train.getScheduleList());
        entity.setAllSeatsNumber(train.getAllSeatsNumber());
        entity.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        entity.setTrainType(TrainType.Regional);

        Train requestEntity = trainRepository.save(entity);
        return trainMapper.mapToDto(requestEntity);
    }

    @Transactional
    TrainDTO decreaseAvailableSeatsAmount(TrainDTO dto) {
        var actualSeatsAmount = dto.getAvailableSeatsNumber();
        var train = trainRepository.findByTrainId(dto.getTrainId());
        train.setAvailableSeatsNumber(actualSeatsAmount - 1);
        var updatedTrain = trainRepository.save(train);
        dto.setAvailableSeatsNumber(actualSeatsAmount - 1);
        return dto;
    }

    @Transactional(readOnly = true)
    public TrainDTO getTrainByTrainId(String id) {
        TrainDTO train = new TrainDTO();
        Train trainEntity = trainRepository.findByTrainId(id);

        if (trainEntity == null) throw new RuntimeException("Train with id: " + id + " is not found");

        return trainMapper.mapToDto(trainEntity);
    }

    @Transactional
    public TrainDTO updateTrain(String id, TrainDTO train) {
        TrainDTO resultTrain = new TrainDTO();
        Train trainEntity = trainRepository.findByTrainId(id);
        if (trainEntity == null) throw new RuntimeException("Train with id: " + id + " is not found");
        //if(userRepository.findByUserId(train.g())==null) throw new RuntimeException("Train with id: " + train.getTrainId() + " is not found");
        trainEntity.setAllSeatsNumber(train.getAllSeatsNumber());
        trainEntity.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        trainEntity.setTrainType(train.getTrainType());
        trainEntity.setScheduleList(train.getScheduleList());


        Train updatedEntity = trainRepository.save(trainEntity);
        BeanUtils.copyProperties(updatedEntity, resultTrain);
        return resultTrain;
    }

    @Transactional
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


    public List<Train> searchTrainsByRoots(List<Root> rootDtoList) {
        List<Train> trainList = new ArrayList<>();
        for (Root root : rootDtoList) {
            trainList.add(trainRepository.findByRootId(root.getRootId()));
        }
        return trainList;
    }


//    @Transactional
//    public LocalDateTime getPathTime(Train train, Station a, Station b) {
//        boolean resultFlag = false;
//        LocalDateTime arrivalDateTime;
//        for (Path path : train.getRoot().getLinkedPaths()) {
//            if (pathService.containsStation(path, a)) {
//                resultFlag = true;
//                //arrivalDateTime =
//                break;
//            }
//        }
//        if (resultFlag) {
//            for (Path path : train.getRoot().getLinkedPaths()) {
//                if (pathService.containsStation(path, b)) {
//                    break;
//                } else {
//                    resultFlag = false;
//                }
//            }
//        }
//        //return arrivalDateTime;
//        return null;
//    }
}
