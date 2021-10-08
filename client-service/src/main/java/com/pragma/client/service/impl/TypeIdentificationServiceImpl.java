package com.pragma.client.service.impl;

import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.repository.TypeIdentificationRepository;
import com.pragma.client.service.TypeIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeIdentificationServiceImpl implements TypeIdentificationService {

    @Autowired
    TypeIdentificationRepository typeIdentificationRepository;

    @Override
    public TypeIdentification getTypeIdentificationByAbbreviation(String abbreviation) {
        return typeIdentificationRepository.findByAbbreviation(abbreviation);
    }

}
