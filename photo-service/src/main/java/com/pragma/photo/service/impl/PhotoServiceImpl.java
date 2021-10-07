package com.pragma.photo.service.impl;

import com.pragma.photo.entity.Photo;
import com.pragma.photo.repository.PhotoRepository;
import com.pragma.photo.service.PhotoService;
import com.pragma.photo.utilities.SequenceUtils.SequenceService;
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

    @Autowired
    private SequenceService sequenceService;

    private static final String PHOTO_NAME = "client_photo_";

    @Override
    public Photo addPhoto(MultipartFile file) throws IOException {

        Photo photo = new Photo();
        photo.setPhoto(file.getBytes());
        photo.setPhotoNumber(sequenceService.getNextValue("photo"));
        photo.setName(PHOTO_NAME + photo.getPhotoNumber());
        return photoRepository.save(photo);

    }

    @Override
    public Photo addPhoto(Photo photo) {

        photo.setPhotoNumber(sequenceService.getNextValue("photo"));
        photo.setName(PHOTO_NAME + photo.getPhotoNumber());
        return photoRepository.save(photo);

    }

    @Override
    public Photo getPhoto(String id) {
        return photoRepository.findById(id).orElse(null);
    }

    @Override
    public Photo updatePhoto(String id, Photo photo) {

        Photo photoQuery = photoRepository.findById(id).orElse(null);

        if (photoQuery == null) {
            return null;
        }
        photoQuery.setPhoto(photo.getPhoto());
        return photoRepository.save(photoQuery);
    }

    @Override
    public void deletePhoto(Photo photo) {

        Photo photoDB = getPhoto(photo.getId());
        if (photoDB == null){
            return;
        }
        photoRepository.delete(photoDB);
    }
}
