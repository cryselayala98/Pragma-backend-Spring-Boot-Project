package com.pragma.photo.service;

import com.pragma.photo.entity.Photo;
import com.pragma.photo.repository.PhotoRepository;
import com.pragma.photo.service.impl.PhotoServiceImpl;
import com.pragma.photo.utilities.SequenceUtils.SequenceService;
import com.pragma.photo.utilities.data.DataFaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {

    @InjectMocks
    PhotoServiceImpl photoServiceImplTest;

    @Mock
    PhotoRepository photoRepositoryTest;

    @Mock
    SequenceService sequenceServiceTest;

    @BeforeEach
    void config(){
        MockitoAnnotations.initMocks(this);
        photoServiceImplTest = new PhotoServiceImpl(photoRepositoryTest, sequenceServiceTest);
    }


    @Test
    public void addPhoto(){

        Mockito.when(photoRepositoryTest.save(any()))
                .thenReturn(DataFaker.getPredeterminatedPhoto());
        Mockito.when(sequenceServiceTest.getNextValue(any()))
                .thenReturn(DataFaker.ANY_PHOTO_NUMBER);
        Photo photoSaved = photoServiceImplTest.addPhoto(DataFaker.getPredeterminatedPhotoPreSave());
        Assertions.assertThat(photoSaved).isEqualTo(DataFaker.getPredeterminatedPhoto());
        Mockito.verify(photoRepositoryTest).save(any());
    }

    @Test
    public void getPhoto(){

        Photo photoTestFirst = DataFaker.getAnyPhoto();
        Mockito.when(photoRepositoryTest.findById(anyString()))
                .thenReturn(Optional.of(photoTestFirst));
        Photo photoResult = photoServiceImplTest.getPhoto(photoTestFirst.getId());
        Assertions.assertThat(photoResult).isEqualTo(photoTestFirst);
        Mockito.verify(photoRepositoryTest).findById(anyString());
    }

    @Test
    public void updatePhoto(){
        Mockito.when(photoRepositoryTest.save(any()))
                .thenReturn(DataFaker.getPredeterminatedPhoto());
        Mockito.when(photoRepositoryTest.findById(anyString()))
                .thenReturn(Optional.of(DataFaker.getPredeterminatedPhoto()));
        Photo photoUpdated = photoServiceImplTest.updatePhoto(DataFaker.ANY_PHOTO_ID, DataFaker.getPredeterminatedPhoto());
        Assertions.assertThat(photoUpdated).isEqualTo(DataFaker.getPredeterminatedPhoto());
        Mockito.verify(photoRepositoryTest).save(any());
    }

    @Test
    public void deletePhoto(){

        Photo photoTest = DataFaker.getAnyPhoto();
        Mockito.when(photoRepositoryTest.findById(anyString()))
                .thenReturn(Optional.of(photoTest));
        Mockito.doNothing().when(photoRepositoryTest).delete(any());
        photoServiceImplTest.deletePhoto(photoTest);
        Mockito.verify(photoRepositoryTest).delete(any());
    }
}
