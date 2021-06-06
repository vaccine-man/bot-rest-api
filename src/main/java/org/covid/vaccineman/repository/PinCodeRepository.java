package org.covid.vaccineman.repository;

import org.covid.vaccineman.entity.PinCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface PinCodeRepository extends JpaRepository<PinCodeEntity, Long> {

    PinCodeEntity findPinCodeEntityByPinCode(Integer pinCode);
}
