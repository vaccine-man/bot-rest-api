package org.covid.vaccineman.repository;

import org.covid.vaccineman.entity.PinCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface PinCodeRepository extends JpaRepository<PinCodeEntity, Long> {
    @Query("SELECT p FROM PinCodeEntity p WHERE p.pinCode=?1")
    PinCodeEntity findByPinCode(Integer pinCode);
}
