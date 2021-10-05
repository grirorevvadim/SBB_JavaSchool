package com.tsystems.javaschool.projects.SBB.service.implementation;

import com.tsystems.javaschool.projects.SBB.io.entity.RequestEntity;
import com.tsystems.javaschool.projects.SBB.repository.RequestRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.RequestService;
import com.tsystems.javaschool.projects.SBB.shared.Utils;
import com.tsystems.javaschool.projects.SBB.shared.dataTransferObject.RequestDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationStatusResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public RequestDTO createRequest(RequestDTO request) {
        RequestEntity entity = new RequestEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setUserId(request.getUserId());
        entity.setRequestId(utils.generateId(30));

        RequestEntity requestEntity = requestRepository.save(entity);
        RequestDTO resultRequest = new RequestDTO();
        BeanUtils.copyProperties(requestEntity, resultRequest);
        return resultRequest;
    }

    @Override
    public RequestDTO getRequestByRequestId(String id) {

        RequestDTO request = new RequestDTO();
        RequestEntity requestEntity = requestRepository.findByRequestId(id);

        if (requestEntity == null) throw new RuntimeException("Request with id: " + id + " is not found");
        BeanUtils.copyProperties(requestEntity, request);

        return request;
    }

    @Override
    public RequestDTO updateRequest(String id, RequestDTO request) {
        RequestDTO resultRequest = new RequestDTO();
        RequestEntity requestEntity = requestRepository.findByRequestId(id);
        if (requestEntity == null) throw new RuntimeException("Request with id: " + id + " is not found");
        if (userRepository.findByUserId(request.getUserId()) == null)
            throw new RuntimeException("User with id: " + request.getUserId() + " is not found");
        requestEntity.setUserId(request.getUserId());

        RequestEntity updatedEntity = requestRepository.save(requestEntity);
        BeanUtils.copyProperties(updatedEntity, resultRequest);
        return resultRequest;
    }

    @Override
    public String deleteRequest(String id) {
        RequestEntity resultEntity = requestRepository.findByRequestId(id);

        if (resultEntity == null) throw new RuntimeException("Request with id: " + id + " is not found");

        String result;
        requestRepository.delete(resultEntity);
        resultEntity = requestRepository.findByRequestId(id);
        if (resultEntity != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("Request with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }
}
