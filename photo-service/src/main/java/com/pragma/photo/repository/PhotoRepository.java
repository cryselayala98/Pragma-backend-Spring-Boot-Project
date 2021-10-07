package com.pragma.photo.repository;

import com.pragma.photo.entity.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PhotoRepository extends MongoRepository<Photo,String> {
}
