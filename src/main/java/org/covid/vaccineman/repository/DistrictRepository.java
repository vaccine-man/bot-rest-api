package org.covid.vaccineman.repository;

import org.covid.vaccineman.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {

    @Query("SELECT d.districtId FROM DistrictEntity d WHERE d.districtName LIKE %?1%")
    int getDistrictByName(String name);
}
