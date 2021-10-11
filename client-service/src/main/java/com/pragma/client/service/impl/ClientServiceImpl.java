package com.pragma.client.service.impl;

import com.pragma.client.client.PhotoClient;
import com.pragma.client.entity.City;
import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.model.Photo;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.repository.ClientRepository;
import com.pragma.client.repository.TypeIdentificationRepository;
import com.pragma.client.service.CityService;
import com.pragma.client.service.ClientService;
import com.pragma.client.service.TypeIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TypeIdentificationService typeIdentificationService;

    @Autowired
    CityService cityService;

    @Autowired
    PhotoClient photoClient;

    @Override
    public List<Client> findClientAll() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> findByAgeGreaterThanEqual(Integer age) {
        return clientRepository.findByAgeGreaterThanEqual(age);
    }

    @Override
    public Client getClient(TypeIdentification typeDocument, String numberIdentification) {
        //falta agregar la busqueda con foto
        return clientRepository.findByTypeIdentificationAndNumberIdentification(typeDocument, numberIdentification);
    }

    @Override
    public Client getClient(Long clientId) {
        //falta agregar la busqueda con foto
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public Client createClient(Client client) throws IOException {

        City cityQuery = cityService.getCity(client.getCity().getId());
        if(cityQuery == null){
            client.setCity(null);
            return client;
        }

        client.setCity(cityQuery);

        TypeIdentification typeIdentificationQuery = typeIdentificationService.getTypeIdentificationByAbbreviation(client.getTypeIdentification().getAbbreviation());
        if(typeIdentificationQuery == null){
            client.setTypeIdentification(null);
            return client;
        }

        client.setTypeIdentification(typeIdentificationQuery);

        Client clientQuery = clientRepository.findByNumberIdentification (client.getNumberIdentification());

        if (clientQuery != null){
            return  clientQuery;
        }

        Photo photoCreate = photoClient.addPhoto(client.getPhoto()).getBody();

        client.setPhoto(photoCreate);

        client.setState("CREATED");
        client = clientRepository.save(client);

        return client;
    }

    @Override
    public Client updateClient(Client client) {
        Client clientQuery = getClient(client.getId());

        if (clientQuery == null){
            return  null;
        }

        clientQuery.setName(client.getName());
        clientQuery.setLastName(client.getLastName());
        clientQuery.setAge(client.getAge());
        clientQuery.setNumberIdentification(client.getNumberIdentification());
        clientQuery.setTypeIdentification(client.getTypeIdentification());
        clientQuery.setCity(client.getCity());

        //update photo

        return clientRepository.save(clientQuery);
    }

    @Override
    public Client deleteClient(Client client) {

        Client clientQuery = getClient(client.getId());

        if (clientQuery == null){
            return  null;
        }

        clientQuery.setState("DELETED");
        return clientRepository.save(clientQuery);
    }
}
