package com.pragma.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.client.entity.City;
import com.pragma.client.service.CityService;
import com.pragma.client.utilities.Validation.FormatErrorString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityRest {

    @Autowired
    CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> listAllCities() {
        List<City> cities = cityService.findCityAll();
        if (cities.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(cities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<City> getCity(@PathVariable("id") long id) {
        log.info("Fetching city with id {}", id);
        City city  = cityService.getCity(id);

        if (city == null) {
            log.error("City with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<City> createCity(@Valid @RequestBody City city, BindingResult result) throws IOException {

        log.info("Creating city : {}", city);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }


        City cityCreate = cityService.createCity(city);

        return  ResponseEntity.status( HttpStatus.CREATED).body(cityCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCity(@PathVariable("id") long id, @Valid @RequestBody City city, BindingResult result) {
        log.info("Updating City with id {}", id);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }

        City currentCity = cityService.updateCity(id, city);

        if (currentCity == null) {
            log.error("Unable to update. City with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(currentCity);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<City> deleteCity(@PathVariable("id") long id) {
        log.info("Fetching & Deleting City with id {}", id);

        City cityDelete = cityService.getCity(id);
        if (cityDelete == null) {
            log.error("Unable to delete. City with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        cityService.deleteCity(cityDelete);

        return ResponseEntity.ok(cityDelete);
    }

}
