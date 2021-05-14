package org.covid.vaccineman.service;

import org.covid.vaccineman.dto.district.DistrictCreateRequest;
import org.covid.vaccineman.entity.DistrictEntity;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.covid.vaccineman.repository.DistrictRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class DistrictService {

    private DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public int getDistrictByName(String name) throws ResourceNotFoundException {
        int districtId = districtRepository.getDistrictByName(name);
        if(isEmpty(districtId)) {
            throw new ResourceNotFoundException("District " + name + " does not exist");
        }
        return districtId;
    }

    public void createDistricts(List<DistrictCreateRequest> request) {
        List<DistrictEntity> entities = new ArrayList<>();

        request.forEach(district ->  {
            DistrictEntity entity = new DistrictEntity();
            entity.setDistrictId(district.getDistrictId());
            entity.setDistrictName(district.getDistrictName().toLowerCase());

            entities.add(entity);
        });

        districtRepository.saveAll(entities);
    }
}
