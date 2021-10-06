package com.pragma.client.repository;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    public List<Client> findByAgeGreaterThanEqual(Integer age);
    public Client findByTypeIdentificationAndNumberIdentification(TypeIdentification type, String numberIdentification);
    public Client findByNumberIdentification(String numberIdentification);

}
