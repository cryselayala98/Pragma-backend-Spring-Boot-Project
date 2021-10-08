package com.pragma.client.repository;

import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeIdentificationRepository extends JpaRepository<TypeIdentification,Long> {
    public TypeIdentification findByAbbreviation(String abbreviation);
}
