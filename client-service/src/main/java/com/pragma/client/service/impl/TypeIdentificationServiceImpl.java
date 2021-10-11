package com.pragma.client.service.impl;

import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.repository.TypeIdentificationRepository;
import com.pragma.client.repository.TypeIdentificationRepository;
import com.pragma.client.service.TypeIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeIdentificationServiceImpl implements TypeIdentificationService {

    @Autowired
    TypeIdentificationRepository typeIdentificationRepository;

    @Override
    public TypeIdentification getTypeIdentificationByAbbreviation(String abbreviation) {
        return typeIdentificationRepository.findByAbbreviation(abbreviation);
    }

    @Override
    public TypeIdentification createTypeIdentification(TypeIdentification typeIdentification) {

        TypeIdentification typeIdentificationQuery = typeIdentificationRepository.findByAbbreviation(typeIdentification.getAbbreviation());
        if(typeIdentificationQuery != null) return typeIdentificationQuery;

        return typeIdentificationRepository.save(typeIdentification);
    }

    @Override
    public TypeIdentification getTypeIdentification(Long typeIdentificationId) {
        return typeIdentificationRepository.findById(typeIdentificationId).orElse(null);
    }

    @Override
    public TypeIdentification updateTypeIdentification(Long id, TypeIdentification typeIdentification) {
        TypeIdentification typeIdentificationQuery = getTypeIdentification(id);

        if(typeIdentificationQuery == null) return null;

        if(!typeIdentificationQuery.getAbbreviation().equals(typeIdentification.getAbbreviation())){
            TypeIdentification typeIdentificationRepeated = typeIdentificationRepository.findByAbbreviation(typeIdentification.getAbbreviation());
            if(typeIdentificationRepeated != null) return typeIdentificationRepeated;
        }

        typeIdentificationQuery.setName(typeIdentification.getName());
        typeIdentificationQuery.setAbbreviation(typeIdentification.getAbbreviation());

        return typeIdentificationRepository.save(typeIdentificationQuery);
    }

    @Override
    public void deleteTypeIdentification(TypeIdentification typeIdentification) {

        try {
            typeIdentificationRepository.delete(typeIdentification);
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public List<TypeIdentification> findTypeIdentificationAll() {
        return typeIdentificationRepository.findAll();
    }

}
