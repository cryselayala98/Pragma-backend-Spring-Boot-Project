package com.pragma.client.repository;

import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    public List<Client> findByAgeGreaterThanEqual(Integer age);

    @Query("SELECT c from Client c JOIN TypeIdentification ti ON c.typeIdentification = ti WHERE ti.abbreviation = :abbreviationIdentification AND c.numberIdentification = :numberIdentification ")
    public Client findByTypeIdentificationAndNumberIdentification(String abbreviationIdentification, String numberIdentification);

    public Client findByNumberIdentification(String numberIdentification);

}
