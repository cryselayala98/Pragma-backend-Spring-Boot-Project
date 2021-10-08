package com.pragma.client.service;

import com.pragma.client.entity.City;
import com.pragma.client.entity.TypeIdentification;

public interface TypeIdentificationService {

        public TypeIdentification getTypeIdentificationByAbbreviation(String abbreviation);

}
