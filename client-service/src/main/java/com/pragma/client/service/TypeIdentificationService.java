package com.pragma.client.service;

import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.entity.TypeIdentification;

import java.util.List;

public interface TypeIdentificationService {

        public TypeIdentification getTypeIdentificationByAbbreviation(String abbreviation);

        public TypeIdentification createTypeIdentification(TypeIdentification typeIdentification);

        public TypeIdentification getTypeIdentification(Long typeIdentificationId);

        public TypeIdentification updateTypeIdentification(Long id, TypeIdentification typeIdentification);

        public void deleteTypeIdentification(TypeIdentification typeIdentification);

        public List<TypeIdentification> findTypeIdentificationAll();

}
