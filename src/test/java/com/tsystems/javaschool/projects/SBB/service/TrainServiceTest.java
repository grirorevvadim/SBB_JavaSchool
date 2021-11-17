package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TrainServiceTest {
    @InjectMocks
    TrainService trainService;

    @Mock
    TrainRepository trainRepository;

    @Mock
    TrainMapper trainMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void getTrainByTrainNumber() {
        Train train = new Train();
        TrainDTO dto = new TrainDTO();
        train.setTrainType(TrainType.Regional);
        train.setSectionPrice(100);
        train.setId(1234L);
        dto.setTrainType(TrainType.Regional);
        dto.setSectionPrice(100);
        dto.setId(1234L);
        when(trainRepository.findByTrainNumber(anyString())).thenReturn(train);
        when(trainMapper.mapToDto(train)).thenReturn(dto);

        TrainDTO testTrain = trainService.getTrainByTrainNumber("1234");
        verify(trainRepository, times(1)).findByTrainNumber(anyString());
        verify(trainMapper, times(1)).mapToDto(any(Train.class));
    }
}