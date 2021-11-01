package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final RootService rootService;
    private final TrainMapper trainMapper;

    @Transactional
    public TrainDTO createTrain(TrainDTO train) {
        train.setRoot(rootService.getRootByRootId(train.getRoot().getId()));
        Train entity = trainMapper.mapToEntity(train);

        Train requestEntity = trainRepository.save(entity);
        return trainMapper.mapToDto(requestEntity);
    }

    @Transactional
    public void decreaseAvailableSeatsAmount(TrainDTO dto) {
        var actualSeatsAmount = trainRepository.getById(dto.getId()).getAvailableSeatsNumber();
        var train = trainRepository.getById(dto.getId());
        train.setAvailableSeatsNumber(actualSeatsAmount - 1);
        var updatedTrain = trainRepository.save(train);
        dto.setAvailableSeatsNumber(actualSeatsAmount - 1);
    }

    @Transactional(readOnly = true)
    public TrainDTO getTrainByTrainId(Long id) {
        TrainDTO train = new TrainDTO();
        Train trainEntity = trainRepository.getById(id);
        return trainMapper.mapToDto(trainEntity);
    }


    @Transactional
    public void updateTrain(TrainDTO train) {
        RootDTO rootDTO = rootService.getRootByRootId(train.getRoot().getId());
        train.setRoot(rootDTO);
        Train train1 = trainMapper.mapToEntity(train);
        trainRepository.save(train1);
    }

    @Transactional
    public void deleteTrain(Long id) {
        Train resultEntity = trainRepository.getById(id);
        trainRepository.delete(resultEntity);
    }


    public List<Train> searchTrainsByRoots(List<Root> rootDtoList) {
        List<Train> trainList = new ArrayList<>();
        for (Root root : rootDtoList) {
            trainList.add(trainRepository.getById(root.getId()));
        }
        return trainList;
    }

    public List<TrainDTO> findAll() {
        var trains = trainRepository.findAll();
        List<TrainDTO> dtos = new ArrayList<>();
        for (Train train : trains) {
            dtos.add(trainMapper.mapToDto(train));
        }
        return dtos;
    }

    public boolean checkValidDepartureDate(String date) {
        boolean res = false;
        if (date == null) date = LocalDate.now().toString();
        LocalDate localDate = LocalDate.now();
        if (localDate.isBefore(LocalDate.parse(date)) || localDate.isEqual(LocalDate.parse(date))) res = true;
        return res;
    }

    public TrainDTO getTrainByNumber(String trainNumber) {
        Train train = trainRepository.findByTrainNumber(trainNumber);
        return trainMapper.mapToDto(train);
    }

    public TrainDTO prepareTrain(TrainDTO trainDTO) {
        trainDTO.setDepartureName("AA");
        trainDTO.setArrivalName("BB");
        trainDTO.setDepartureDate(LocalDate.now().toString());
        return trainDTO;
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
