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

        List<Client> listClients = clientRepository.findAll();

        if(listClients.isEmpty()) return listClients;

        listClients.forEach(client ->{
            Photo photo = photoClient.getPhoto(client.getPhotoId()).getBody();
            client.setPhoto(photo);
        });

        return listClients;
    }

    @Override
    public List<Client> findByAgeGreaterThanEqual(Integer age) {

        List<Client> listClients = clientRepository.findByAgeGreaterThanEqual(age);

        if(listClients.isEmpty()) return listClients;

        listClients.forEach(client ->{
            Photo photo = photoClient.getPhoto(client.getPhotoId()).getBody();
            client.setPhoto(photo);
        });

        return listClients;
    }

    @Override
    public Client getClient(String documentAbbreviation, String numberIdentification) {

        Client clientQuery = clientRepository.findByTypeIdentificationAndNumberIdentification(documentAbbreviation, numberIdentification);

        if(clientQuery == null){
            return null;
        }

        Photo photoQuery = photoClient.getPhoto(clientQuery.getPhotoId()).getBody();
        clientQuery.setPhoto(photoQuery);

        return clientQuery;
    }

    @Override
    public Client getClient(Long clientId) {

        Client clientQuery = clientRepository.findById(clientId).orElse(null);

        if(clientQuery == null){
            return null;
        }

        Photo photoQuery = photoClient.getPhoto(clientQuery.getPhotoId()).getBody();
        clientQuery.setPhoto(photoQuery);

        return clientQuery;
    }

    @Override
    public Client createClient(Client client) throws IOException {

        Client clientQuery = clientRepository.findByNumberIdentification (client.getNumberIdentification());

        if (clientQuery != null){
            return  clientQuery;
        }

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

        Photo photoCreate = photoClient.addPhoto(client.getPhoto()).getBody();

        client.setPhoto(photoCreate);
        client.setPhotoId(photoCreate.getId());

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

        if(!client.getTypeIdentification().getAbbreviation().equals(clientQuery.getTypeIdentification().getAbbreviation()) || !client.getNumberIdentification().equals(clientQuery.getNumberIdentification())){
            Client clientQueryRepeated = getClient(client.getTypeIdentification().getAbbreviation(), client.getNumberIdentification());

            if (clientQueryRepeated != null){
                return  clientQueryRepeated;
            }
        }

        City cityQuery = cityService.getCity(client.getCity().getId());
        if(cityQuery == null){
            client.setCity(null);
            return client;
        }

        TypeIdentification typeIdentificationQuery = typeIdentificationService.getTypeIdentificationByAbbreviation(client.getTypeIdentification().getAbbreviation());
        if(typeIdentificationQuery == null){
            client.setTypeIdentification(null);
            return client;
        }

        clientQuery.setName(client.getName());
        clientQuery.setLastName(client.getLastName());
        clientQuery.setAge(client.getAge());
        clientQuery.setNumberIdentification(client.getNumberIdentification());
        clientQuery.setTypeIdentification(typeIdentificationQuery);
        clientQuery.setCity(cityQuery);

        Photo photoUpdate = photoClient.updatePhoto(client.getPhotoId(), client.getPhoto()).getBody();

        client.setPhoto(photoUpdate);

        return clientRepository.save(clientQuery);
    }

    @Override
    public Client deleteClient(Client client) {

        Client clientQuery = getClient(client.getId());

        if (clientQuery == null){
            return null;
        }

        photoClient.deletePhoto(clientQuery.getPhotoId());
        clientQuery.setState("DELETED");
        return clientRepository.save(clientQuery);
    }
    //public ResponseEntity<List<CustomerDto>> findByIdType(IdType idType)
}
