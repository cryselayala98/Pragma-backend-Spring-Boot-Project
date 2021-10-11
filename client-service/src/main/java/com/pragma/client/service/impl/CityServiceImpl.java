package com.pragma.client.service.impl;

import com.pragma.client.entity.City;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.service.CityService;
import com.pragma.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ClientService clientService;

    @Override
    public City createCity(City city) {

        City citySearch = cityRepository.findByName(city.getName());
        if(citySearch != null) return citySearch;

        return cityRepository.save(city);
    }

    @Override
    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @Override
    public City updateCity(Long id, City city) {
        City cityQuery = getCity(id);

        if(cityQuery == null) return null;

        if(!cityQuery.getName().equals(city.getName())){
            City citRepeated = cityRepository.findByName(city.getName());
            if(citRepeated != null) return citRepeated;
        }

        cityQuery.setName(city.getName());

        return cityRepository.save(cityQuery);
    }

    @Override
    public void deleteCity(City city) {
        try {
            cityRepository.delete(city);
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public List<City> findCityAll() {

        return cityRepository.findAll();
    }

}
