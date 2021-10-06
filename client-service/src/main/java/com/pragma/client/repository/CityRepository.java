package com.pragma.client.repository;

import com.pragma.client.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
    public City findByName(String nameCity);
}
