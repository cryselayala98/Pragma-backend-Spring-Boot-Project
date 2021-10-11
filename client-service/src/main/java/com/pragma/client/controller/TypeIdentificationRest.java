package com.pragma.client.controller;

import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.service.TypeIdentificationService;
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
@RequestMapping("/typeIdentification")
public class TypeIdentificationRest {

    @Autowired
    TypeIdentificationService typeIdentificationService;

    @GetMapping
    public ResponseEntity<List<TypeIdentification>> listAllTypeIdentification() {
        List<TypeIdentification> cities = typeIdentificationService.findTypeIdentificationAll();
        if (cities.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(cities);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TypeIdentification> getTypeIdentification(@PathVariable("id") long id) {
        log.info("Fetching typeIdentification with id {}", id);
        TypeIdentification typeIdentification  = typeIdentificationService.getTypeIdentification(id);

        if (typeIdentification == null) {
            log.error("TypeIdentification with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(typeIdentification);
    }

    @GetMapping(value = "/{abbreviation}")
    public ResponseEntity<TypeIdentification> getTypeIdentification(@PathVariable("id") String abbreviation) {
        log.info("Fetching typeIdentification with abbreviation {}", abbreviation);
        TypeIdentification typeIdentification  = typeIdentificationService.getTypeIdentificationByAbbreviation(abbreviation);

        if (typeIdentification == null) {
            log.error("TypeIdentification with abbreviation {} not found.", abbreviation);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(typeIdentification);
    }

    @PostMapping
    public ResponseEntity<TypeIdentification> createTypeIdentification(@Valid @RequestBody TypeIdentification typeIdentification, BindingResult result) throws IOException {

        log.info("Creating typeIdentification : {}", typeIdentification);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }


        TypeIdentification typeIdentificationCreate = typeIdentificationService.createTypeIdentification(typeIdentification);

        return  ResponseEntity.status( HttpStatus.CREATED).body(typeIdentificationCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTypeIdentification(@PathVariable("id") long id, @Valid @RequestBody TypeIdentification typeIdentification, BindingResult result) {
        log.info("Updating TypeIdentification with id {}", id);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }

        TypeIdentification currentTypeIdentification = typeIdentificationService.updateTypeIdentification(id, typeIdentification);

        if (currentTypeIdentification == null) {
            log.error("Unable to update. TypeIdentification with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(currentTypeIdentification);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TypeIdentification> deleteTypeIdentification(@PathVariable("id") long id) {
        log.info("Fetching & Deleting TypeIdentification with id {}", id);

        TypeIdentification typeIdentificationDelete = typeIdentificationService.getTypeIdentification(id);
        if (typeIdentificationDelete == null) {
            log.error("Unable to delete. TypeIdentification with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        typeIdentificationService.deleteTypeIdentification(typeIdentificationDelete);

        return ResponseEntity.ok(typeIdentificationDelete);
    }

}
