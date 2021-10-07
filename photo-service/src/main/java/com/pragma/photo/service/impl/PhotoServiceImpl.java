package com.pragma.photo.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pragma.photo.entity.Photo;
import com.pragma.photo.repository.PhotoRepository;
import com.pragma.photo.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    // ModelMapper modelMapper = new ModelMapper();

    @Override
    public Photo addPhoto(MultipartFile file) throws IOException {

        Photo photo = new Photo();
        photo.setPhoto(file.getBytes());
        photo = photoRepository.save(photo);
        log.info("se agregó la foto juaz juaz");
        //Photo photoDB = modelMapper.map(photo, Photo.class);
        return photo;
    }

    @Override
    public Photo addPhoto(Photo photo) {
        return null;
    }

    @Override
    public Photo getPhoto(String id) {
        return null;
    }

    @Override
    public Photo updatePhoto(String id, Photo photo) {
        return null;
    }

    @Override
    public void deletePhoto(Photo photo) {

    }
}
