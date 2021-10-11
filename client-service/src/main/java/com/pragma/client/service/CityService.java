package com.pragma.client.service;

import com.pragma.client.entity.City;
import java.util.List;

public interface CityService {

    public City createCity(City city);

    public City getCity(Long cityId);

    public City updateCity(Long id, City city);

    public void deleteCity(City city);

    public List<City> findCityAll();

}
