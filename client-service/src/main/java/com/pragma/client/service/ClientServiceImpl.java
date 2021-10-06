package com.pragma.client.service;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.repository.CityRepository;
import com.pragma.client.repository.ClientRepository;
import com.pragma.client.repository.TypeIdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public List<Client> findfindByAgeGreaterThanEqual(Integer age) {
        return clientRepository.findByAgeGreaterThanEqual(age);
    }

    @Override
    public Client getClient(TypeIdentification typeDocument, String numberIdentification) {
        return clientRepository.findByTypeIdentificationAndNumberIdentification(typeDocument, numberIdentification);
    }

    @Override
    public Client createClient(Client client) {

        Client clientQuery = clientRepository.findByNumberIdentification (client.getNumberIdentification()));

        //devolver objeto si ya existe
        if (clientQuery !=null){
            return  clientQuery;
        }

        //actualizar foto
        productClient.updateStockProduct(item.getProductId(), item.getQuantity() * -1);

        client.setState("CREATED");
        client = clientRepository.save(client);

        return client;
    }

    @Override
    public Client updateClient(Client client) {
        CX invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Client deleteClient(Client client) {
        return null;
    }
}
