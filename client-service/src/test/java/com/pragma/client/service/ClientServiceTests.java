package com.pragma.client.service;

import com.pragma.client.client.PhotoClient;
import com.pragma.client.entity.Client;
import com.pragma.client.model.Photo;
import com.pragma.client.repository.ClientRepository;
import com.pragma.client.service.impl.ClientServiceImpl;
import com.pragma.client.utilities.data.ClientDataFaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @InjectMocks
    ClientServiceImpl clientServiceTest;

    @Mock
    ClientRepository clientRepositoryTest;

    @Mock
    TypeIdentificationService typeIdentificationServiceTest;

    @Mock
    CityService cityServiceTest;

    @Mock
    PhotoClient photoClientTest;

    @BeforeEach
    void config(){
        MockitoAnnotations.initMocks(this);
        clientServiceTest = new ClientServiceImpl(clientRepositoryTest, typeIdentificationServiceTest,cityServiceTest,photoClientTest);
    }

    @Test
    public void findClientAll(){
        Client clientFirst = ClientDataFaker.getAnyClient();
        Client clientSecond = ClientDataFaker.getAnyClient();

        List<Client> clients = new ArrayList<Client>();
        clients.add(clientFirst);
        clients.add(clientSecond);
        Page<Client> pageClients = new PageImpl<>(clients);
        Pageable pageable = PageRequest.of(0, clients.size());

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);

        Mockito.when(clientRepositoryTest.findAll(pageable)).thenReturn(pageClients);
        Mockito.when(photoClientTest.getPhoto(anyString())).thenReturn(PhotoResponse);

        Page<Client> clientQuery = clientServiceTest.findClientAll(pageable);

        Assertions.assertThat(clientQuery.getContent()).isEqualTo(clients);

        clientQuery.getContent().forEach(clientResult ->
                Assertions.assertThat(clientResult.getPhoto()).isEqualTo(ClientDataFaker.ANY_CLIENT_PHOTO)
        );

        Mockito.verify(clientRepositoryTest).findAll(pageable);
        Mockito.verify(photoClientTest, Mockito.times(clients.size())).getPhoto(anyString());
    }

    @Test
    public void findByAgeGreaterThanEqual(){
        Client clientFirst = ClientDataFaker.getAnyClient();
        Client clientSecond = ClientDataFaker.getAnyClient();

        List<Client> clients = new ArrayList<Client>();
        clients.add(clientFirst);
        clients.add(clientSecond);

        int possibleAge = 100;
        Mockito.when(clientRepositoryTest.findByAgeGreaterThanEqual(possibleAge)).thenReturn(clients);

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.getPhoto(anyString())).thenReturn(PhotoResponse);

        List<Client> clientQuery = clientServiceTest.findByAgeGreaterThanEqual(possibleAge);

        Assertions.assertThat(clientQuery).isEqualTo(clients);

        clientQuery.forEach(clientResult ->
                Assertions.assertThat(clientResult.getPhoto()).isEqualTo(ClientDataFaker.ANY_CLIENT_PHOTO)
        );

        Mockito.verify(clientRepositoryTest).findByAgeGreaterThanEqual(possibleAge);
        Mockito.verify(photoClientTest, Mockito.times(clients.size())).getPhoto(anyString());
    }

    @Test
    public void findByAgeGreaterThanEqualEmpty(){

        int impossibleAge = 130;
        Mockito.when(clientRepositoryTest.findByAgeGreaterThanEqual(impossibleAge)).thenReturn(new ArrayList<Client>());

        List<Client> clientQuery = clientServiceTest.findByAgeGreaterThanEqual(impossibleAge);

        Assertions.assertThat(clientQuery).isEqualTo(new ArrayList<Client>());

        Mockito.verify(clientRepositoryTest).findByAgeGreaterThanEqual(impossibleAge);
        Mockito.verify(photoClientTest, Mockito.times(0)).getPhoto(anyString());
    }

    @Test
    public void getClientById(){
        Client clientQueryExpected = ClientDataFaker.getAnyClient();

        Mockito.when(clientRepositoryTest.findById(anyLong()))
                .thenReturn(Optional.of(clientQueryExpected));

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.getPhoto(anyString())).thenReturn(PhotoResponse);

        Client clientQuery = clientServiceTest.getClient(clientQueryExpected.getId());
        Assertions.assertThat(clientQuery).isEqualTo(clientQueryExpected);

        Mockito.verify(clientRepositoryTest).findById(anyLong());
        Mockito.verify(photoClientTest).getPhoto(anyString());
    }

    @Test
    public void getClientByDocument(){
        Client clientQueryExpected = ClientDataFaker.getPredeterminatedClient();

        Mockito.when(clientRepositoryTest.findByTypeIdentificationAndNumberIdentification(anyString(), anyString()))
                .thenReturn(clientQueryExpected);

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.getPhoto(anyString())).thenReturn(PhotoResponse);

        Client clientQuery = clientServiceTest.getClient(ClientDataFaker.ANY_TYPE_IDENTIFICATION_ABBREVIATION, ClientDataFaker.ANY_CLIENT_NUMBER_IDENTIFICATION);

        Assertions.assertThat(clientQuery).isEqualTo(clientQueryExpected);
        Assertions.assertThat(clientQuery.getPhoto()).isEqualTo(PhotoResponse.getBody());

        Mockito.verify(clientRepositoryTest).findByTypeIdentificationAndNumberIdentification(anyString(),anyString());
        Mockito.verify(photoClientTest).getPhoto(anyString());
    }

    @Test
    public void getClientByDocumentEmpty(){
        Client clientQueryExpected = ClientDataFaker.getPredeterminatedClient();

        Mockito.when(clientRepositoryTest.findByTypeIdentificationAndNumberIdentification(anyString(), anyString()))
                .thenReturn(null);

        Client clientQuery = clientServiceTest.getClient(ClientDataFaker.ANY_TYPE_IDENTIFICATION_ABBREVIATION, ClientDataFaker.ANY_CLIENT_NUMBER_IDENTIFICATION);

        Assertions.assertThat(clientQuery).isEqualTo(null);

        Mockito.verify(clientRepositoryTest).findByTypeIdentificationAndNumberIdentification(anyString(),anyString());
        Mockito.verify(photoClientTest, Mockito.times(0)).getPhoto(anyString());
    }

    @Test
    public void createClient() throws Exception {

        Client clientExpected = ClientDataFaker.getPredeterminatedClient();

        Mockito.when(clientRepositoryTest.findByTypeIdentificationAndNumberIdentification(anyString(), anyString()))
                .thenReturn(null);
        Mockito.when(cityServiceTest.getCity(anyLong()))
                .thenReturn(ClientDataFaker.ANY_CLIENT_CITY);
        Mockito.when(typeIdentificationServiceTest.getTypeIdentificationByAbbreviation(anyString()))
                .thenReturn(ClientDataFaker.ANY_CLIENT_TYPE_IDENTIFICATION);
        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.addPhoto(ClientDataFaker.ANY_CLIENT_PHOTO_PRE_SAVE))
                .thenReturn(PhotoResponse);
        Mockito.when(clientRepositoryTest.save(any()))
                .thenReturn(ClientDataFaker.getPredeterminatedClient());

        Client clientSaved = clientServiceTest.createClient(ClientDataFaker.getPredeterminatedClientPreSave());

        Assertions.assertThat(clientSaved).isEqualTo(clientExpected);

        Mockito.verify(clientRepositoryTest).findByTypeIdentificationAndNumberIdentification(anyString(), anyString());
        Mockito.verify(cityServiceTest).getCity(anyLong());
        Mockito.verify(typeIdentificationServiceTest).getTypeIdentificationByAbbreviation(anyString());
        Mockito.verify(photoClientTest).addPhoto(ClientDataFaker.ANY_CLIENT_PHOTO_PRE_SAVE);
    }

    @Test
    public void updateClient(){
        Client clientQuery = ClientDataFaker.getPredeterminatedClient();

        Mockito.when(clientRepositoryTest.findById(anyLong()))
                .thenReturn(Optional.of(clientQuery));

        Mockito.when(cityServiceTest.getCity(anyLong()))
                .thenReturn(ClientDataFaker.ANY_CLIENT_CITY);

        Mockito.when(typeIdentificationServiceTest.getTypeIdentificationByAbbreviation(anyString()))
                .thenReturn(ClientDataFaker.ANY_CLIENT_TYPE_IDENTIFICATION);

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.getPhoto(anyString()))
                .thenReturn(PhotoResponse);
        Mockito.when(photoClientTest.updatePhoto(ClientDataFaker.ANY_CLIENT_PHOTO_ID, ClientDataFaker.ANY_CLIENT_PHOTO))
                .thenReturn(PhotoResponse);

        Mockito.when(clientRepositoryTest.save(any()))
                .thenReturn(clientQuery);

        Client clientSaved = clientServiceTest.updateClient(clientQuery);

        Assertions.assertThat(clientSaved).isEqualTo(clientQuery);

        Mockito.verify(clientRepositoryTest).findById(anyLong());
        Mockito.verify(photoClientTest).getPhoto(anyString());
        Mockito.verify(cityServiceTest).getCity(anyLong());
        Mockito.verify(typeIdentificationServiceTest).getTypeIdentificationByAbbreviation(anyString());
        Mockito.verify(photoClientTest).updatePhoto(ClientDataFaker.ANY_CLIENT_PHOTO_ID, ClientDataFaker.ANY_CLIENT_PHOTO);
    }

    @Test
    public void deleteClient(){

        Mockito.when(clientRepositoryTest.findById(anyLong()))
                .thenReturn(Optional.of(ClientDataFaker.getPredeterminatedClient()));

        ResponseEntity<Photo> PhotoResponse = new ResponseEntity<>(ClientDataFaker.ANY_CLIENT_PHOTO, HttpStatus.OK);
        Mockito.when(photoClientTest.getPhoto(anyString()))
                .thenReturn(PhotoResponse);

        Mockito.when(photoClientTest.deletePhoto(anyString()))
                .thenReturn(PhotoResponse);
        Mockito.when(clientRepositoryTest.save(any()))
                .thenReturn(ClientDataFaker.getPredeterminatedClientDeleted());

        Client clientSaved = clientServiceTest.deleteClient(ClientDataFaker.getPredeterminatedClient());

        Assertions.assertThat(clientSaved).isEqualTo(ClientDataFaker.getPredeterminatedClientDeleted());

        Mockito.verify(clientRepositoryTest).findById(anyLong());
        Mockito.verify(photoClientTest).getPhoto(anyString());
        Mockito.verify(photoClientTest).deletePhoto(any());
    }
}
