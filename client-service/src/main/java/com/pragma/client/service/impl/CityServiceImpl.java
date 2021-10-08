package com.pragma.client.service.impl;

import com.pragma.client.entity.City;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

}
