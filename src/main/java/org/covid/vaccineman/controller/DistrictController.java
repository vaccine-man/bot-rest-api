package org.covid.vaccineman.controller;

import org.covid.vaccineman.dto.district.DistrictCreateRequest;
import org.covid.vaccineman.service.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.covid.vaccineman.constant.ApiConstants.DISTRICTS_API;

@RestController
public class DistrictController {

    private DistrictService districtService;

    DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping(value = DISTRICTS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDistrict(@Valid @RequestBody List<DistrictCreateRequest> requests) {
        districtService.createDistricts(requests);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
