package com.pragma.client.controller;

import com.pragma.client.entity.Client;
import com.pragma.client.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.client.utilities.Validation.ErrorMessage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientRest {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> listAllClients() {
        List<Client> clients = clientService.findClientAll();
        if (clients.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(clients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") long id) {
        log.info("Fetching client with id {}", id);
        Client client  = clientService.getClient(id);

        if (client == null) {
            log.error("Client with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(client);
    }

    @GetMapping(value = "/document/{abbreviation}/{documentNumber}")
    public ResponseEntity<Client> getClientByDocument(@PathVariable("abbreviation") String documentAbbreviation, @PathVariable("documentNumber") String documentNumber) {
        log.info("Fetching client with document {}", (documentAbbreviation + ": " + documentNumber));
        Client client  = clientService.getClient(documentAbbreviation, documentNumber);

        if (client == null) {
            log.error("Client with document {} not found.", (documentAbbreviation + ": " + documentNumber));
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(client);
    }

    @GetMapping(value = "/agemin/{age}")
    public ResponseEntity<List<Client>> getClientByMinAge(@PathVariable("age") Integer age) {
        log.info("Fetching clients older than {} years", age);
        List<Client> listClient  = clientService.findByAgeGreaterThanEqual(age);

        if (listClient.isEmpty()) {
            log.error("Client with age older than {} years not found.", age);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(listClient);
    }


    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client, BindingResult result) throws IOException {

        log.info("Creating client : {}", client);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }


        Client clientCreate = clientService.createClient(client);

        if(clientCreate.getCity() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo de ciudad no válido");
        }

        if(clientCreate.getTypeIdentification() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo tipo de identificación no válido");
        }

        return  ResponseEntity.status( HttpStatus.CREATED).body(clientCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateClient(@PathVariable("id") long id, @Valid @RequestBody Client client, BindingResult result) {
        log.info("Updating Client with id {}", id);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatErrorString.formatMessage(result));
        }

        client.setId(id);
        Client currentClient = clientService.updateClient(client);

        if (currentClient == null) {
            log.error("Unable to update. Client with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }

        if(currentClient.getCity() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo de ciudad no válido");
        }

        if(currentClient.getTypeIdentification() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo tipo de identificación no válido");
        }

        return  ResponseEntity.ok(currentClient);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Client with id {}", id);

        Client clientDelete = clientService.getClient(id);
        if (clientDelete == null) {
            log.error("Unable to delete. Client with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        clientDelete = clientService.deleteClient(clientDelete);
        return ResponseEntity.ok(clientDelete);
    }

}
