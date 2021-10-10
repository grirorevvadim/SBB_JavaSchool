package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RequestDTO;

public interface RequestService {
    RequestDTO createRequest(RequestDTO request);

    RequestDTO getRequestByRequestId(String id);

    RequestDTO updateRequest(String id, RequestDTO request);

    String deleteRequest(String id);
}
