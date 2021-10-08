package com.pragma.client.controller;

import com.pragma.client.entity.City;
import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.service.CityService;
import com.pragma.client.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.client.service.TypeIdentificationService;
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

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client, BindingResult result) throws IOException {

        log.info("Creating client : {}", client);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
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
    public ResponseEntity<?> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        log.info("Updating Client with id {}", id);

        client.setId(id);
        Client currentClient = clientService.updateClient(client);

        if (currentClient == null) {
            log.error("Unable to update. Client with id {} not found.", id);
            return  ResponseEntity.notFound().build();
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

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
