package org.covid.vaccineman.service;

import org.covid.vaccineman.entity.PinCodeEntity;
import org.covid.vaccineman.exception.ResourceNotFoundException;
import org.covid.vaccineman.repository.PinCodeRepository;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class PinCodeService {

    private PinCodeRepository repository;

    public PinCodeService(PinCodeRepository repository) {
        this.repository = repository;
    }

    public void isPinCodeValid(Integer pinCode) throws ResourceNotFoundException {
        PinCodeEntity pinCodeEntity = repository.findByPinCode(pinCode);
        if(isEmpty(pinCodeEntity)) {
            throw new ResourceNotFoundException("pin_code " + pinCode + " does not exist");
        }
    }
}
