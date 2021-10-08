package com.pragma.client.repository;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public List<Client> findByAgeGreaterThanEqual(Integer age);
    public Client findByTypeIdentificationAndNumberIdentification(TypeIdentification type, String numberIdentification);
    public Client findByNumberIdentification(String numberIdentification);

}
