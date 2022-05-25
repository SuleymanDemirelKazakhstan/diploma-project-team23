package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.RequestToFoundationDto;
import kz.sdu.project.sauapbackend.dto.response.ServerDefaultResponse;

import java.util.List;

public interface RequestsToFoundationService {

    ServerDefaultResponse newRequest(RequestToFoundationDto request);

    List<RequestToFoundationDto> getRequestsByFoundationId(Long foundationId);

}
