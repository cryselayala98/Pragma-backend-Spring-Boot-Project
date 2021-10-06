package com.pragma.client.repository;

import com.pragma.client.entity.TypeIdentification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeIdentificationRepository extends JpaRepository<TypeIdentification,Long> {
    public TypeIdentification findByName(String nameNumberIdentification);
}
