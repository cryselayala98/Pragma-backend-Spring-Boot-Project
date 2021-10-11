package com.pragma.client.service;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    public Page<Client> findClientAll(Pageable pageable);
    public List<Client> findByAgeGreaterThanEqual(Integer age);
    public Client getClient(String DocumentAbbreviation, String numberIdentification);
    public Client getClient(Long clientId);

    public Client createClient(Client client) throws IOException;
    public Client updateClient(Client client);
    public Client deleteClient(Client client);


}
