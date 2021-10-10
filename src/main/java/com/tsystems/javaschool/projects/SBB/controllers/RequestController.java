package com.tsystems.javaschool.projects.SBB.controllers;

import com.tsystems.javaschool.projects.SBB.service.RequestService;
import com.tsystems.javaschool.projects.SBB.domain.dto.RequestDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.OperationStatus;
import com.tsystems.javaschool.projects.SBB.ui.models.request.RequestDetailsModel;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationName;
import com.tsystems.javaschool.projects.SBB.ui.models.response.RequestRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @GetMapping(path = "/{id}")
    public RequestRest getRequest(@PathVariable String id) {
        RequestRest requestRest = new RequestRest();
        RequestDTO requestDTO = requestService.getRequestByRequestId(id);
        BeanUtils.copyProperties(requestDTO, requestRest);
        return requestRest;
    }

    @PostMapping
    public RequestRest postRequest(@RequestBody RequestDetailsModel requestDetails) {
        RequestRest requestRest = new RequestRest();
        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(requestDetails, requestDTO);
        RequestDTO createdRequest = requestService.createRequest(requestDTO);
        BeanUtils.copyProperties(createdRequest, requestRest);
        return requestRest;
    }

    @PutMapping(path = "/{id}")
    public RequestRest updateRequest(@PathVariable String id, @RequestBody RequestDetailsModel requestDetails) {
        RequestRest requestRest = new RequestRest();
        RequestDTO requestDTO = new RequestDTO();
        BeanUtils.copyProperties(requestDetails, requestDTO);
        requestDTO = requestService.updateRequest(id, requestDTO);
        BeanUtils.copyProperties(requestDTO, requestRest);
        return requestRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteRequest(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(requestService.deleteRequest(id));
        return status;
    }
}
