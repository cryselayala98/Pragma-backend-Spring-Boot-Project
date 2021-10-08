package com.pragma.client.service;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.repository.ClientRepository;
import com.pragma.client.repository.TypeIdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TypeIdentificationRepository typeIdentificationRepository;

    @Autowired
    CityRepository cityRepository;

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
        return clientRepository.findByTypeIdentificationAndNumberIdentification(typeDocument, numberIdentification);
    }

    @Override
    public Client getClient(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public Client createClient(Client client) {

        Client clientQuery = clientRepository.findByNumberIdentification (client.getNumberIdentification());

        //devolver objeto si ya existe
        if (clientQuery !=null){
            return  clientQuery;
        }

        //actualizar foto llamando al client de photo


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

        return clientRepository.save(clientQuery);
    }

    @Override
    public Client deleteClient(Client client) {
        return null;
    }
}
