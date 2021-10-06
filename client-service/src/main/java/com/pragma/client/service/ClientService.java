package com.pragma.client.service;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;

import java.util.List;

public interface ClientService {
    public List<Client> findClientAll();
    public List<Client> findfindByAgeGreaterThanEqual(Integer age);
    public Client getClient(TypeIdentification typeDocument, String numberIdentification);

    public Client createClient(Client client);
    public Client updateClient(Client client);
    public Client deleteClient(Client client);


}
