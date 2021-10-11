package com.pragma.client.service;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    public List<Client> findClientAll();
    public List<Client> findByAgeGreaterThanEqual(Integer age);
    public Client getClient(String DocumentAbbreviation, String numberIdentification);
    public Client getClient(Long clientId);

    public Client createClient(Client client) throws IOException;
    public Client updateClient(Client client);
    public Client deleteClient(Client client);


}
