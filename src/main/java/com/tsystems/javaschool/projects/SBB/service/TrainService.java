package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final StationService stationService;
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
    public TrainDTO getTrainByTrainNumber(String trainNumber) {
        return trainMapper.mapToDto(trainRepository.findByTrainNumber(trainNumber));
    }

    @Transactional(readOnly = true)
    public TrainDTO getTrainByTrainId(Long id) {
        return trainRepository.findById(id)
                .map(trainMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Train with id = " + id + " is not found"));
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
        Optional<Train> resultEntity = trainRepository.findById(id);
        if (resultEntity.isEmpty()) throw new EntityNotFoundException("Train with id = " + id + " is not found");
        trainRepository.delete(resultEntity.get());
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
        if (date.isEmpty()) date = LocalDate.now().toString();
        LocalDate localDate = LocalDate.now();
        if (localDate.isBefore(LocalDate.parse(date)) || localDate.isEqual(LocalDate.parse(date))) res = true;
        return res;
    }

    public TrainDTO getTrainByNumber(String trainNumber) {
        Train train = trainRepository.findByTrainNumber(trainNumber);
        if (train == null) throw new EntityNotFoundException("Train with such number was not found");
        return trainMapper.mapToDto(train);
    }

    public TrainDTO prepareTrain(TrainDTO trainDTO) {
        trainDTO.setDepartureName("AA");
        trainDTO.setArrivalName("BB");
        trainDTO.setDepartureDate(LocalDate.now().toString());
        return trainDTO;
    }

    public int getPrice(String trainNumber, String departureName, String arrivalName) {
        TrainDTO train = getTrainByNumber(trainNumber);
        StationDTO departure = stationService.getStationByStationName(departureName);
        StationDTO arrival = stationService.getStationByStationName(arrivalName);
        RootDTO root = train.getRoot();
        int indexDep = rootService.stationIndex(root, departure);
        int indexArr = rootService.stationIndex(root, arrival);
        int length = root.getStationsList().subList(indexDep, indexArr).size();
        return train.getSectionPrice() * length;
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
